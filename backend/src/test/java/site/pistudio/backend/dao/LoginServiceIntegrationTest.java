package site.pistudio.backend.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import site.pistudio.backend.entities.Admin;
import site.pistudio.backend.services.LoginService;
import site.pistudio.backend.services.VerifyTokenService;
import site.pistudio.backend.utils.TokenStatus;

import java.io.IOException;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginServiceIntegrationTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    VerifyTokenService verifyTokenService;

    @Test
    public void verifyToken() throws InterruptedException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                ".eyJhdWQiOiJ5YW51byIsIm5iZiI6MTYwNTcwNDEyOSwiaXNzIjoicGktc3R1ZGlvIiwiZXhwIjoxNjA2MzA4OTI5LCJpYXQiOjE2MDU3MDQxMjl9.VrgTWlWJGYby7a0iTLRzoz3Dlv1KW6HYB54WphjvNgdPIxmcCwpyHJhhzZErODO1TQ-cdwQwapIKfQw_z_Ft5w";
        System.out.println(verifyTokenService.verifyToken(token));

    }

    @Test
    public void requestForOpenId() throws IOException {
        System.out.println(loginService.requestForOpenId("011ojgll2fHh6643Aill220iCR3ojglT"));
    }

    @Test
    public void addAdmin() {
        Admin yanuo = new Admin();
        yanuo.setId(UUID.randomUUID());
        yanuo.setUsername("yanuo");
        yanuo.setPassword(bCryptPasswordEncoder.encode("980508"));
        loginService.generateToken(yanuo, TokenStatus.RENEW);
        adminRepository.save(yanuo);
    }
}
