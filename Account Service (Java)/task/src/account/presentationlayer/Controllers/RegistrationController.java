package account.presentationlayer.Controllers;

import account.businesslayer.user.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody User user) {
        userService.registerUser(user);
        SignupDTO response = new SignupDTO(user.getId(), user.getName(), user.getLastname(), user.getEmail());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/api/auth/changepass")
    public ResponseEntity<Object> changepass(@Valid @RequestBody PasswordChanger passwordChanger,
                                             @AuthenticationPrincipal User user) {
        userService.changeUserPassword(user, passwordChanger.newPassword());
        PasswordChangerDTO response = new PasswordChangerDTO(user.getEmail().toLowerCase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
