package saypaje.SpringSecurity.controller;


import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import saypaje.SpringSecurity.controller.dto.CreateUserDTO;
import saypaje.SpringSecurity.entities.Role;
import saypaje.SpringSecurity.entities.User;
import saypaje.SpringSecurity.repository.RoleRepository;
import saypaje.SpringSecurity.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(CreateUserDTO body) {
        var basicRole = roleRepository.findByName(Role.Value.BASIC.name());

        Optional<User> userDb = userRepository.findUserByName(body.name());

        if (userDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = new User();
        user.setName(body.name());
        user.setPassword(passwordEncoder.encode(body.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
