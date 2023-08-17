package account.businesslayer.security;

import account.businesslayer.user.User;
import account.persistencelayer.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserAdapterService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserAdapterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }
        return new UserAdapter(user);
    }
}
