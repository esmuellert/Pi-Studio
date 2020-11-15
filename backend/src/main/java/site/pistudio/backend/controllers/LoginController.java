package site.pistudio.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.services.LoginService;


@RestController
 @RequestMapping("login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String login(@RequestBody LoginForm body) throws JsonProcessingException {
        String code = body.getCode();
        String token = body.getToken();
        return loginService.login(code, token);
    }

    private static class LoginForm {
        private String code;
        private String token;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


}
