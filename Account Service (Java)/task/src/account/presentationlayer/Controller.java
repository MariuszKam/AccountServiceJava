package account.presentationlayer;


import account.businesslayer.user.User;
import account.businesslayer.user.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class Controller {

    private final UserMapper userMapper;


    public Controller(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userMapper.toSignupDTO(user), HttpStatus.OK);

    }

    @GetMapping("/api/empl/payment/")
    public String payment() {
        return "This is just a test";
    }

}
