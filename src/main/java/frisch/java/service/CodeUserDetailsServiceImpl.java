package frisch.java.service;

import frisch.java.model.CodeUserDetails;
import frisch.java.model.Role;
import frisch.java.model.User;
import frisch.java.repository.UserRepository;
import frisch.java.service.CodeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;

/**
 * Created by luke on 6/21/16.
 */
@Service
@Transactional
public class CodeUserDetailsServiceImpl implements CodeUserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public User registerNewAccount(User newUser) throws Exception {
        if (repository.findByEmail(newUser.getEmail()) != null) {
            throw new Exception("Account already exists " + newUser.getEmail());
        }
        newUser.setRole(Role.ROLE_USER);
        repository.save(newUser);
        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            return null;
//            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        CodeUserDetails principal = CodeUserDetails.getBuilder()
                .firstName(user.getFirstName()).lastName(user.getLastName()).id(user.getId())
                .password(user.getPassword()).role(user.getRole()).username(user.getEmail())
                .build();

        return principal;
    }

}
