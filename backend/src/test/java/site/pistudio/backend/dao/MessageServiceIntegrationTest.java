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
                ".eyJhdWQiOiJlNDExYjlkNS01Nzg4LTQwOGYtYWNiMS0yMGRiYzJjNjI3NjYiLCJuYmYiOjE2MDcwNTMzNjAsImlzcyI6InBpLXN0dWRpbyIsImV4cCI6MTYwNzY1ODE2MCwiaWF0IjoxNjA3MDUzMzYwfQ.4Pr3ytLVfUo7Xm2aw7GomYTGzANSULTyMy8XzlLiFSne3flWr7Ue0UjUcYjYXRZBkw0HYZf8rzAAxQMLZ6ekZA", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJhdWQiOiJ5YW51byIsIm5iZiI6MTYwNzQwOTcyOCwiaXNzIjoicGktc3R1ZGlvIiwiZXhwIjoxNjA4MDE0NTI4LCJpYXQiOjE2MDc0MDk3Mjh9.Bzh6ruvjT9oRhXkzIwUiAlu6qd6ZexfMUaM3GyvuJnvzUDTC_C-BmE79hagSDfm1ut2JxVYGgCIYdigd8tzoJg"};
        Random random = new Random();
        for (int i = 0; i < 82; i++) {
            int number = random.nextInt(2);
            messageService
                    .receiveMessage("test " +
                                    "messagffasfsafasfsafasfasfsafasfasdfdsfasfsdfasdfdsafsafdsfsafsdfasdfdasdfasfasfe" + i,
                            20339117866L, verifyTokenService.verifyToken(tokens[number]));
        }
    }
}
