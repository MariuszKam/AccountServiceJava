package account.presentationlayer.Controllers;

import account.businesslayer.user.User;
import account.businesslayer.user.UserMapper;
import account.persistencelayer.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class PaymentController {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public PaymentController(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @PostMapping("api/acct/payments")

    @PutMapping("api/acct/payments")

    @GetMapping("/api/empl/payment")
    public ResponseEntity<Object> payment(Principal principal) {
        User user = userRepository.findByEmailIgnoreCase(principal.getName());
        return new ResponseEntity<>(userMapper.toSignupDTO(user), HttpStatus.OK);
    }
}
