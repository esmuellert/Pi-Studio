package site.pistudio.backend.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.pistudio.backend.services.MessageService;
import site.pistudio.backend.services.VerifyTokenService;

import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageServiceIntegrationTest {
    @Autowired
    MessageService messageService;

    @Autowired
    VerifyTokenService verifyTokenService;

    @Test
    public void sendMessages() {
        String[] tokens = new String[]{"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                ".eyJhdWQiOiJlNDExYjlkNS01Nzg4LTQwOGYtYWNiMS0yMGRiYzJjNjI3NjYiLCJuYmYiOjE2MDU3MDIzNzEsImlzcyI6InBpLXN0dWRpbyIsImV4cCI6MTYwNjMwNzE3MSwiaWF0IjoxNjA1NzAyMzcxfQ.EGPvbrk7-OCSL85OZ0G73baM3Xvp2cZDOEz4ivB2-Li2ehSDWrAYSc3slMet9RECN_KMpiPeBTp66OiMe2ZSGA",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                        ".eyJhdWQiOiJ5YW51byIsIm5iZiI6MTYwNTc2OTM3NSwiaXNzIjoicGktc3R1ZGlvIiwiZXhwIjoxNjA2Mzc0MTc1LCJpYXQiOjE2MDU3NjkzNzV9.pu-uamivT5BuX6Q2zHLgZBhZIdHeHcu6LA3p6Ib9ppDCBIoOOBLb31hHsZqMUATpxVmwN3cnrnvXkHxKgnEx6w"};
        Random random = new Random();
        for (int i = 0; i < 82; i++) {
            int number = random.nextInt(2);
            messageService
                    .receiveMessage("test message" + i, 20323218021L, verifyTokenService.verifyToken(tokens[number]));
        }
    }
}
