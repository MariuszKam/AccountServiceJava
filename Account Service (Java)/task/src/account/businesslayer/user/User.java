package account.businesslayer.user;


import jakarta.persistence.*;
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
    private final String name;
    private final String lastname;
    private final String email;
    private String password;


    public boolean isEmailCorrect() {
        assert email != null;
        return  email.matches("^[a-zA-Z]+@acme.com$");
    }
    public boolean isCorrect() {
        return isEmailCorrect() &&
                name != null && !name.isEmpty() &&
                lastname != null && !lastname.isEmpty() &&
                email != null && !email.isEmpty() &&
                password != null && !password.isEmpty();
    }

}
