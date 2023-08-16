package account.businesslayer.user;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupDTO {
    private Long id;
    private String name;
    private String lastname;
    private String email;
}
