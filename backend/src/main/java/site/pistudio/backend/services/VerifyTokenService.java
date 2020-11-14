package site.pistudio.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.UserRepository;
import site.pistudio.backend.entities.User;
import site.pistudio.backend.exceptions.InvalidTokenException;

import java.util.List;
import java.util.UUID;

@Service
public class VerifyTokenService {
    UserRepository userRepository;

    public VerifyTokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String verifyToken(String token) {
        String openId;
        try {
            DecodedJWT jwt = JWT.decode(token);
            List<String> idList = jwt.getAudience();
            String id = idList.get(0);
            User user = userRepository.findUserById(UUID.fromString(id));
            Algorithm algorithm = Algorithm.HMAC512(user.getTokenSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("pi-studio")
                    .withAudience(id)
                    .build();
            verifier.verify(token);
            openId = user.getOpenId();
        } catch (RuntimeException e) {
            throw new InvalidTokenException();
        }
        return openId;
    }
}
