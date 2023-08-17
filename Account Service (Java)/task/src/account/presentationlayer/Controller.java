package account.presentationlayer;


import account.businesslayer.exceptions.UserExistsException;
import account.businesslayer.user.User;
import account.businesslayer.user.UserMapper;
import account.persistencelayer.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



@RestController
public class Controller {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public Controller(UserMapper userMapper,
                      PasswordEncoder passwordEncoder,
                      UserRepository userRepository) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody User user) {
        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new UserExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(userMapper.toSignupDTO(user), HttpStatus.OK);

    }

    @GetMapping("/api/empl/payment/")
    public String payment() {
        return "This is just a test";
    }

}
