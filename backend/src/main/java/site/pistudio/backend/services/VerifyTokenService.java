package site.pistudio.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import site.pistudio.backend.dao.UserRepository;
import site.pistudio.backend.entities.User;

import java.util.List;
import java.util.UUID;

public class VerifyTokenService {
    UserRepository userRepository;

    public VerifyTokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginService.TokenStatus verifyToken(String token) {
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
        } catch (RuntimeException e) {
            return LoginService.TokenStatus.INVALID;
        }
        return LoginService.TokenStatus.VALID;
    }
}
