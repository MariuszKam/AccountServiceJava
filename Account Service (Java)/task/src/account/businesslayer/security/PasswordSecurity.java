package account.businesslayer.security;


import account.businesslayer.exceptions.SamePasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;


@Component
public class PasswordSecurity {

    private final PasswordEncoder passwordEncoder;
    private final Set<String> BREACHED_PASSWORDS = Set.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    public PasswordSecurity(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public String setEncodedPassword(String password) {
        verification(password);
        return passwordEncoder.encode(password);

    }

    public void newPasswordVerification(String oldPassword, String newPassword) {
        verification(newPassword);
        if (passwordEncoder.matches(newPassword, oldPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The passwords must be different!");
        }
    }

    private void verification(String password) {
        if (password.length() < 12) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password length must be 12 chars minimum!");
        }
        if (BREACHED_PASSWORDS.contains(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The password is in the hacker's database!");
        }
    }
}
