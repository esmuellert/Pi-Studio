package site.pistudio.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.UserRepository;
import site.pistudio.backend.entities.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
public class LoginService {
    UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(String code, String token) {
        User user = userRepository.findUserByToken(token);
        if (user == null) {
            user = new User();
        }
        user.setOpenId("Yanuo Ma");
        generateToken(user, TokenStatus.NEW);
        LocalDateTime now = LocalDateTime.now();
        user.setRegisterDate(now);
        userRepository.save(user);
        return user.getToken();
    }

    private String getOpenId(String code) {
        return "";
    }

    private void generateToken(User user, TokenStatus status) {
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
                .withAudience("id", id.toString())
                .sign(algorithm);

        if (status == TokenStatus.NEW){
            user.setId(id);
        }
        user.setToken(token);
        user.setTokenExpired(expiresAt);
        user.setTokenSecret(secret);
    }



    private enum TokenStatus {
        NEW, EXPIRED, UPDATE
    }

}
