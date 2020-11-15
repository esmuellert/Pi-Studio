package site.pistudio.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.pistudio.backend.dao.UserRepository;
import site.pistudio.backend.entities.User;
import site.pistudio.backend.exceptions.InvalidCodeException;
import site.pistudio.backend.utils.TokenStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class LoginService {
    UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String code, String token) throws JsonProcessingException {
        TokenStatusAndUser tokenStatusObject = verifyToken(token);
        User user = new User();

        if (tokenStatusObject.tokenStatus == TokenStatus.NEW) {
            String openId = getOpenId(code);
            User checkIfUserExisted = userRepository.findUserByOpenId(openId);
            if (checkIfUserExisted != null) {
                token = generateToken(checkIfUserExisted, TokenStatus.RENEW);
                userRepository.save(checkIfUserExisted);
                return token;
            }
            token = generateToken(user, tokenStatusObject.tokenStatus);
            user.setOpenId(openId);
            user.setRegisterDate(LocalDateTime.now());
        } else if (tokenStatusObject.tokenStatus == TokenStatus.RENEW) {
            user = tokenStatusObject.user;
            token = generateToken(user, tokenStatusObject.tokenStatus);
        } else {
            return token;
        }
        userRepository.save(user);
        return token;
    }

    private String getOpenId(String code) throws JsonProcessingException {
        Map<String, String> responseMap = requestForOpenId(code);
        if (responseMap.containsKey("errcode") || !responseMap.containsKey("openid")) {
            throw new InvalidCodeException();
        }
        return responseMap.get("openid");
    }

    private String generateToken(User user, TokenStatus status) {
        UUID id;
        if (status == TokenStatus.NEW) {
            id = UUID.randomUUID();
        } else {
            id = user.getId();
        }

        byte[] secret = new byte[64];
        new Random(Instant.now().toEpochMilli()).nextBytes(secret);
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plusWeeks(1);

        Algorithm algorithm = Algorithm.HMAC512(secret);
        String token = JWT.create()
                .withIssuer("pi-studio")
                .withIssuedAt(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant()))
                .withNotBefore(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .withAudience(id.toString())
                .sign(algorithm);

        if (status == TokenStatus.NEW) {
            user.setId(id);
        }
        user.setTokenExpired(expiresAt);
        user.setTokenSecret(secret);
        return token;
    }

    private TokenStatusAndUser verifyToken(String token) {

        DecodedJWT jwt;
        JWTVerifier verifier;
        User user;
        try {
            jwt = JWT.decode(token);
            List<String> idList = jwt.getAudience();
            String id = idList.get(0);
            user = userRepository.findUserById(UUID.fromString(id));
            Algorithm algorithm = Algorithm.HMAC512(user.getTokenSecret());
            verifier = JWT.require(algorithm)
                    .withIssuer("pi-studio")
                    .withAudience(id)
                    .build();
            verifier.verify(token);
        } catch (RuntimeException e) {
            return new TokenStatusAndUser(null, TokenStatus.NEW);
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.plusDays(1).isAfter(user.getTokenExpired())) {
            return new TokenStatusAndUser(user, TokenStatus.RENEW);
        }

        return new TokenStatusAndUser(user, TokenStatus.VALID);
    }

    public Map<String, String> requestForOpenId(String code) throws JsonProcessingException {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String appId = "wxd79dee47f1d31c2e";
        String secret = "62afde5eb0c2e61981b018354608e5e4";
        String grantType = "authorization_code";
        url = url + "?" + "appid=" + appId + "&" + "secret=" + secret + "&" + "js_code=" + code + "&" + "grant_type"
                + "=" + grantType;
        RestTemplate restTemplate = new RestTemplate();
        String responseObject = restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseObject,
                new TypeReference<Map<String, String>>() {
                });
    }




    private static class TokenStatusAndUser {
        private final User user;
        private final TokenStatus tokenStatus;

        public TokenStatusAndUser(User user, TokenStatus tokenStatus) {
            this.user = user;
            this.tokenStatus = tokenStatus;
        }
    }

}
