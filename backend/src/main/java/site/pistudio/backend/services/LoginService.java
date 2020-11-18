package site.pistudio.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import site.pistudio.backend.dao.AdminRepository;
import site.pistudio.backend.dao.UserRepository;
import site.pistudio.backend.entities.Admin;
import site.pistudio.backend.entities.Role;
import site.pistudio.backend.entities.User;
import site.pistudio.backend.exceptions.IllegalAdminException;
import site.pistudio.backend.exceptions.InvalidCodeException;
import site.pistudio.backend.utils.TokenStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AdminRepository adminRepository;

    public LoginService(UserRepository userRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.adminRepository = adminRepository;
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

    private String getOpenId(String code) throws JsonProcessingException {
        Map<String, String> responseMap = requestForOpenId(code);
        if (responseMap.containsKey("errcode") || !responseMap.containsKey("openid")) {
            throw new InvalidCodeException();
        }
        return responseMap.get("openid");
    }

    private Map<String, String> requestForOpenId(String code) throws JsonProcessingException {
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

    public String generateToken(Role role, TokenStatus status) {

        boolean isAdmin = role.getClass().equals(Admin.class);
        String id;
        if (status == TokenStatus.NEW) {
            id = UUID.randomUUID().toString();
        } else if (isAdmin) {
            id = ((Admin) role).getUsername();
        } else {
            id = role.getId().toString();
        }

        byte[] secret;
        LocalDateTime issuedAt;
        LocalDateTime expiresAt;
        if (isAdmin && status == TokenStatus.GENERATE) {
            secret = role.getTokenSecret();
            issuedAt = role.getTokenExpired().minusWeeks(1);
            expiresAt = role.getTokenExpired();
        } else {
            secret = new byte[64];
            new Random(Instant.now().toEpochMilli()).nextBytes(secret);
            issuedAt = LocalDateTime.now();
            expiresAt = issuedAt.plusWeeks(1);
        }


        Algorithm algorithm = Algorithm.HMAC512(secret);

        String token = JWT.create()
                .withIssuer("pi-studio")
                .withIssuedAt(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant()))
                .withNotBefore(Date.from(issuedAt.atZone(ZoneId.systemDefault()).toInstant()))
                .withAudience(id)
                .sign(algorithm);

        if (!isAdmin || status != TokenStatus.GENERATE) {
            if (status == TokenStatus.NEW) {
                role.setId(UUID.fromString(id));
            }
            role.setTokenExpired(expiresAt);
            role.setTokenSecret(secret);
        }
        return token;
    }

    public String adminLogin(String username, String password) throws IllegalAdminException {
        Admin admin = adminRepository.findAdminByUsername(username);
        if (admin == null || !bCryptPasswordEncoder.matches(password, admin.getPassword())) {
            throw new IllegalAdminException();
        }
        String token;
        if (admin.getTokenExpired().isBefore(LocalDateTime.now())) {
            token = generateToken(admin, TokenStatus.RENEW);
            adminRepository.save(admin);
        } else {
            token = generateToken(admin, TokenStatus.GENERATE);
        }
        return token;
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
