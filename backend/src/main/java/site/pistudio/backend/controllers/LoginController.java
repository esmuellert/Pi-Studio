package site.pistudio.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import site.pistudio.backend.dto.ClientLoginBody;
import site.pistudio.backend.services.LoginService;


@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public String login(@RequestBody ClientLoginBody body) throws JsonProcessingException {
        String code = body.getCode();
        String token = body.getToken();
        return loginService.login(code, token);
    }


}
