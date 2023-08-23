package account.businesslayer.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PasswordChangerDTO {
    private final String email;
    private final String status;

    public PasswordChangerDTO(String email) {
        this.email = email;
        this.status = "The password has been updated successfully";
    }


}
