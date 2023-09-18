package account.businesslayer.user;



import account.businesslayer.security.PasswordSecurity;
import account.persistencelayer.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordSecurity passwordSecurity;


    public UserService(UserRepository userRepository, PasswordSecurity passwordSecurity) {
        this.userRepository = userRepository;
        this.passwordSecurity = passwordSecurity;
    }


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee does not exist"));
    }

    public void registerUser(User user) {
        userExists(user.getEmail());
        saveUser(user);
    }

    public void changeUserPassword(User user, String newPassword) {
        passwordSecurity.newPasswordVerification(user.getPassword(), newPassword);
        user.setPassword(newPassword);
        saveUser(user);
    }

    private void userExists(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");
        }
    }

    private void saveUser(User user) {
        user.setPassword(passwordSecurity.setEncodedPassword(user.getPassword()));
        userRepository.save(user);
    }
}
