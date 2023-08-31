package account.presentationlayer.Controllers;

import account.businesslayer.exceptions.PasswordBreachedException;
import account.businesslayer.exceptions.PasswordLengthException;
import account.businesslayer.exceptions.SamePasswordException;
import account.businesslayer.exceptions.UserExistsException;
import account.businesslayer.security.PasswordSecurity;
import account.businesslayer.user.PasswordChanger;
import account.businesslayer.user.PasswordChangerDTO;
import account.businesslayer.user.User;
import account.businesslayer.user.UserMapper;
import account.persistencelayer.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Locale;

@RestController
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordSecurity passwordSecurity;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public RegistrationController(UserRepository userRepository, PasswordSecurity passwordSecurity,
                                  PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordSecurity = passwordSecurity;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserExistsException();
        }
        passwordSecurity.verification(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(userMapper.toSignupDTO(user), HttpStatus.OK);

    }

    @PostMapping("/api/auth/changepass")
    public ResponseEntity<Object> changepass(@Valid @RequestBody PasswordChanger passwordChanger,
                                             Principal principal) {
        passwordSecurity.verification(passwordChanger.getNewPassword());
        User user = userRepository.findByEmailIgnoreCase(principal.getName());
        if (passwordEncoder.matches(passwordChanger.getNewPassword(), user.getPassword())) {
            throw new SamePasswordException();
        }
        user.setPassword(passwordEncoder.encode(passwordChanger.getNewPassword()));
        userRepository.save(user);

        return new ResponseEntity<>(new PasswordChangerDTO(user.getEmail().toLowerCase(Locale.ROOT)), HttpStatus.OK);
    }
}
