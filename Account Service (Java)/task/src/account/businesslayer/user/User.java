package account.businesslayer.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity
@Table(name = "users")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private final long id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String lastname;
    @NotBlank
    @Email(regexp = "^(.+)@acme.com$")
    private final String email;
    @NotBlank
    private String password;


}
