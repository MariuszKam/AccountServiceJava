package account.persistencelayer;

import account.businesslayer.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);
}
