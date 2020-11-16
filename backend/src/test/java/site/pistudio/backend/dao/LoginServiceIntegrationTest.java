package site.pistudio.backend.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.pistudio.backend.services.LoginService;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginServiceIntegrationTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @Test
    public void verifyToken() throws InterruptedException {
//        String token = loginService.generateToken(new User(), LoginService.TokenStatus.NEW);
//        assertEquals(LoginService.TokenStatus.VALID, loginService.verifyToken(token));
    }

    @Test
    public void requestForOpenId() throws IOException {
//        System.out.println(loginService.requestForOpenId("041jXAFa169vYz0oalHa1fwNm64jXAF8"));
    }
}
