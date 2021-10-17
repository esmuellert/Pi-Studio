package site.pistudio.backend.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import site.pistudio.backend.dao.mysql.AdminRepository;
import site.pistudio.backend.entities.mysql.Admin;
import site.pistudio.backend.exceptions.IllegalAdminException;
import site.pistudio.backend.utils.TokenStatus;

@Service
public class PasswordService {
    final AdminRepository adminRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    final LoginService loginService;

    public PasswordService(AdminRepository adminRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           LoginService loginService) {
        this.adminRepository = adminRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.loginService = loginService;
    }

    public String resetPassword(String username, String oldPassword, String newPassword) throws IllegalAdminException {
        Admin admin = adminRepository.findAdminByUsername(username);
        if (admin == null || !bCryptPasswordEncoder.matches(oldPassword, admin.getPassword())) {
            throw new IllegalAdminException();
        }

        admin.setPassword(bCryptPasswordEncoder.encode(newPassword));
        String token = loginService.generateToken(admin, TokenStatus.RENEW);
        adminRepository.save(admin);
        return token;
    }
}
