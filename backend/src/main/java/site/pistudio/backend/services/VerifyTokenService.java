package site.pistudio.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.AdminRepository;
import site.pistudio.backend.dao.UserRepository;
import site.pistudio.backend.entities.Admin;
import site.pistudio.backend.entities.Role;
import site.pistudio.backend.entities.User;
import site.pistudio.backend.exceptions.InvalidTokenException;

import java.util.List;
import java.util.UUID;

@Service
public class VerifyTokenService {
    UserRepository userRepository;
    AdminRepository adminRepository;

    public VerifyTokenService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    public String verifyToken(String token) {
        String openId;
        Role role;
        try {
            DecodedJWT jwt = JWT.decode(token);
            List<String> idList = jwt.getAudience();
            String id = idList.get(0);
            if (id.length() < 30) {
                role = adminRepository.findAdminByUsername(id);
            } else {
                role = userRepository.findUserById(UUID.fromString(id));
            }
            Algorithm algorithm = Algorithm.HMAC512(role.getTokenSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("pi-studio")
                    .withAudience(id)
                    .build();
            verifier.verify(token);
            if (role.getClass().equals(Admin.class)) {
                openId = "admin";
            } else {
                assert role instanceof User;
                openId = ((User) role).getOpenId();
            }
        } catch (RuntimeException e) {
            throw new InvalidTokenException();
        }
        return openId;
    }
}
