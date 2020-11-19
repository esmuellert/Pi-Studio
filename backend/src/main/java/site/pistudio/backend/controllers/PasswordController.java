package site.pistudio.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.pistudio.backend.exceptions.IllegalAdminException;
import site.pistudio.backend.services.PasswordService;

import java.util.Map;

@RestController
@RequestMapping("password")
public class PasswordController {
    final
    PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String resetPassword(@RequestBody Map<String, String> body) throws IllegalAdminException {
        String username = body.get("username");
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");
        return passwordService.resetPassword(username, oldPassword, newPassword);
    }
}
