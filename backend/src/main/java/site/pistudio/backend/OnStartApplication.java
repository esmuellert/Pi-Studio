//package site.pistudio.backend;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import site.pistudio.backend.dao.firestore.AdminRepository;
//import site.pistudio.backend.entities.firestore.Admin;
//import site.pistudio.backend.services.LoginService;
//import site.pistudio.backend.utils.TokenStatus;
//
//import java.util.UUID;
//
//@Component
//public class OnStartApplication implements CommandLineRunner {
//    private final AdminRepository adminRepository;
//    private final LoginService loginService;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    public OnStartApplication(AdminRepository adminRepository, LoginService loginService,
//                              BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.adminRepository = adminRepository;
//        this.loginService = loginService;
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        Admin admin = adminRepository.findByUsername("yanuo");
//        if (admin == null) {
//            Admin yanuo = new Admin();
//            yanuo.setUsername("yanuo");
//            yanuo.setId(UUID.randomUUID());
//            yanuo.setPassword(bCryptPasswordEncoder.encode("980508"));
//            loginService.generateToken(yanuo, TokenStatus.RENEW);
//            adminRepository.save(yanuo);
//        }
//    }
//}
