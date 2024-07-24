package saypaje.SpringSecurity.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import saypaje.SpringSecurity.controller.dto.LoginRequestDto;
import saypaje.SpringSecurity.controller.dto.LoginResponseDto;
import saypaje.SpringSecurity.entities.User;
import saypaje.SpringSecurity.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto body) {
        Optional<User> user = this.userRepository.findUserByName(body.name());

        if (user.isEmpty() || !user.get().isLoginCorrect(body, passwordEncoder)) {
            throw new BadCredentialsException("User or password is invalid!");
        }


        var timeActuallity = Instant.now();
        var timeTokenExpiresIn = 300;

        var claims = JwtClaimsSet.builder().issuer("myBackend")
                .subject(user.get().getId().toString())
                .issuedAt(timeActuallity)
                .expiresAt(timeActuallity.plusSeconds(timeTokenExpiresIn)).build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponseDto(jwtValue,timeTokenExpiresIn));

    }

}
