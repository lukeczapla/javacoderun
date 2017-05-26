package frisch.java.repository;

import frisch.java.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by luke on 6/21/16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
