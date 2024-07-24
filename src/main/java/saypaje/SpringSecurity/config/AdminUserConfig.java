package saypaje.SpringSecurity.config;


import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import saypaje.SpringSecurity.entities.Role;
import saypaje.SpringSecurity.entities.User;
import saypaje.SpringSecurity.repository.RoleRepository;
import saypaje.SpringSecurity.repository.UserRepository;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(Role.Value.ADMIN.name());
        var userAdmin = userRepository.findUserByName("admin");

        if (userAdmin.isPresent()) {
            System.out.println("Admin already exists");
        }

        var user = new User();
        user.setName("Admin2");
        user.setPassword(passwordEncoder.encode("123456"));

        userRepository.save(user);

    }

}
