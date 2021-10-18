package site.pistudio.backend.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import site.pistudio.backend.dao.firestore.AdminRepository;
import site.pistudio.backend.dao.firestore.UserRepository;
import site.pistudio.backend.entities.firestore.Admin;
import site.pistudio.backend.entities.firestore.User;
import site.pistudio.backend.services.LoginService;
import site.pistudio.backend.services.VerifyTokenService;
import site.pistudio.backend.utils.TokenStatus;

import java.io.IOException;
import java.time.LocalDateTime;
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
        Admin admin =  adminRepository.findByUsername("yanuo");
        if (admin == null) {
            Admin yanuo = new Admin();
            yanuo.setUsername("yanuo");
            yanuo.setId(UUID.randomUUID());
            yanuo.setPassword(bCryptPasswordEncoder.encode("980508"));
            loginService.generateToken(yanuo, TokenStatus.NEW);
            adminRepository.save(yanuo);
        }
    }

    @Test
    public void addUser() {
        User user = new User();
        user.setRegisterDate(LocalDateTime.now());
        user.setOpenId("oMj9c5HpP1mV6zjQ53UobYd22gFY");
        loginService.generateToken(user, TokenStatus.NEW);
        userRepository.save(user);
    }

    @Test
    public void findUser() {
        User user = userRepository.findUserByOpenId("oMj9c5HpP1mV6zjQ53UobYd22gFY");
    }
}
