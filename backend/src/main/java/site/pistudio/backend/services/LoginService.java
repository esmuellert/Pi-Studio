package site.pistudio.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.pistudio.backend.dto.CodeToSessionObject;
import site.pistudio.backend.entities.User;
import site.pistudio.backend.exceptions.TokenIllegalException;
import site.pistudio.backend.service.UserRepository;

import java.io.IOException;
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

    public String login(String code, String token) {


        User user = new User();

        user.setOpenId("Yanuo Ma");
        token = generateToken(user, TokenStatus.NEW);
        LocalDateTime now = LocalDateTime.now();
        user.setRegisterDate(now);
        userRepository.save(user);
        return token;
    }

    private String getOpenId(String code) {
        return "";
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

    private TokenStatus verifyToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        List<String> idList = jwt.getAudience();
        if (idList.size() != 1) {
            throw new TokenIllegalException();
        }
        String id = idList.get(0);

        User user = userRepository.findUserById(UUID.fromString(id));
        if (user == null) {
            throw new TokenIllegalException();
        }

        Algorithm algorithm = Algorithm.HMAC512(user.getTokenSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("pi-studio")
                .withAudience(id)
                .build();

        try {
            verifier.verify(token);
        } catch (TokenExpiredException e) {
            return TokenStatus.EXPIRED;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.plusDays(1).isAfter(user.getTokenExpired())) {
            return TokenStatus.RENEW;
        }

        return TokenStatus.VALID;
    }

    public Map<String, String> requestForOpenId(String code) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        String appId = "wxd79dee47f1d31c2e";
        String secret = "62afde5eb0c2e61981b018354608e5e4";
        String grantType = "authorization_code";
        url = url + "?" + "appid=" + appId + "&" + "secret=" + secret + "&" + "js_code=" + code + "&" + "grant_type"
                + "=" + grantType;
        RestTemplate restTemplate = new RestTemplate();
        String responseObject =  restTemplate.getForObject(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(responseObject,
                new TypeReference<Map<String, String>>(){});
        return responseMap;
    }


    private enum TokenStatus {
        NEW, EXPIRED, RENEW, VALID
    }

}
