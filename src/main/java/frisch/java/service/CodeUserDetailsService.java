package frisch.java.service;

import frisch.java.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by luke on 6/22/16.
 */
public interface CodeUserDetailsService extends UserDetailsService {

    public User registerNewAccount(User newUser) throws Exception;

}
