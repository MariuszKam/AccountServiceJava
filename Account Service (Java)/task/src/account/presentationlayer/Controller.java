package account.presentationlayer;


import account.businesslayer.user.SignupDTO;
import account.businesslayer.user.User;
import account.businesslayer.user.UserMapper;
import account.persistencelayer.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class Controller {

    private final UserMapper userMapper;


    public Controller(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/api/auth/signup")
    public SignupDTO signup(@RequestBody User user) {
        if (user.isCorrect()) {
            return userMapper.toSignupDTO(user);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/empl/payment/")
    public String payment() {
        return "This is just a test";
    }

}
