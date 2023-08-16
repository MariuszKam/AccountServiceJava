package account.businesslayer.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    public SignupDTO toSignupDTO(User user) {
        return Objects.isNull(user) ? null : modelMapper.map(user, SignupDTO.class);
    }


    public User toUser(SignupDTO signupDTO) {
        return Objects.isNull(signupDTO) ? null : modelMapper.map(signupDTO, User.class);
    }

}
