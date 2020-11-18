package site.pistudio.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.exceptions.IllegalAdminException;
import site.pistudio.backend.services.LoginService;

import java.util.Map;


@RestController
@RequestMapping("login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String login(@RequestBody Map<String, String> body) throws JsonProcessingException, IllegalAdminException {
        if (body.containsKey("username")) {
            return loginService.adminLogin(body.get("username"), body.get("password"));
        }
        String code = body.get("code");
        String token = body.get("token");
        return loginService.login(code, token);
    }


}
