package account.businesslayer.security;


import account.businesslayer.exceptions.PasswordBreachedException;
import account.businesslayer.exceptions.PasswordLengthException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PasswordSecurity {

    private final Set<String> breachedPasswords = new HashSet<>(List.of(
            "PasswordForJanuary", "PasswordForFebruary",
            "PasswordForMarch", "PasswordForApril",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember"
    ));

    public void verification(String password) {
        if (password.length() < 12) {
            throw new PasswordLengthException();
        }
        if (breachedPasswords.contains(password)) {
            throw new PasswordBreachedException();
        }
    }
}
