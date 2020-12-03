package site.pistudio.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.exceptions.IllegalAdminException;
import site.pistudio.backend.services.LoginService;
import site.pistudio.backend.services.VerifyTokenService;

import java.util.Map;


@RestController
@RequestMapping("login")
public class LoginController {

    private final LoginService loginService;
    private final VerifyTokenService verifyTokenService;

    public LoginController(LoginService loginService,
                           VerifyTokenService verifyTokenService) {
        this.loginService = loginService;
        this.verifyTokenService = verifyTokenService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String login(@RequestBody Map<String, String> body) throws JsonProcessingException, IllegalAdminException {
        if (body.containsKey("username")) {
            return loginService.adminLogin(body.get("username"), body.get("password"));
        }
        if (!body.containsKey("code")) {
            return verifyTokenService.verifyToken(body.get("token"));
        }
        String code = body.get("code");
        String token = body.get("token");
        return loginService.login(code, token);
    }


}
