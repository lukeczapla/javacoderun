package frisch.java.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import frisch.java.conf.GoogleProperties;
import frisch.java.model.User;
import frisch.java.service.CodeUserDetailsService;
import frisch.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by luke on 6/5/16.
 */
@RestController
@Api("User login and registration")
public class UserController {

    @Autowired
    CodeUserDetailsService codeUserDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    GoogleProperties googleProperties;

    private static final JacksonFactory jacksonFactory = new JacksonFactory();

    @RequestMapping(value = "/conf/userlist", method = RequestMethod.GET)
    @ApiOperation("Retrieve list of all registered users")
    public List<User> getAll() {
        return userService.getAll();
    }


    @RequestMapping(value = "/conf/user", method = RequestMethod.GET)
    @ApiOperation("Check user status")
    public Principal user(Principal user) {
        return user;
    }



    @ApiOperation(value = "Authenticate the provided user", notes = "User information obtained from Google")
    @RequestMapping(value = "/conf/user", method = RequestMethod.POST)
    @JsonView(JsonViews.User.class)
    public ResponseEntity login(@RequestBody User user) throws Exception {
        if (user == null || user.getEmail() == null) {
            return new ResponseEntity("Invalid data", HttpStatus.BAD_REQUEST);
        }
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), jacksonFactory)
                .setAudience(Collections.singletonList(googleProperties.getClientId()))
                .build();
        System.out.println("Login Controller: " + user.getEmail());
        GoogleIdToken idToken = verifier.verify(user.getTokenId());
        if (idToken == null) {
            return new ResponseEntity("Invalid token data", HttpStatus.BAD_REQUEST);
        } else {
            Payload payload = idToken.getPayload();
            if (payload == null || !payload.getEmail().equals(user.getEmail())) {
                return new ResponseEntity("Invalid email address", HttpStatus.BAD_REQUEST);
            }
            System.out.println("Verified account");
        }
        if (codeUserDetailsService.loadUserByUsername(user.getEmail()) != null) {
//            System.out.println("Authenticating");

            UsernamePasswordAuthenticationToken authrequest = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
                    codeUserDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authrequest);
//            System.out.println("Success for login controller");
            return new ResponseEntity("Finished, authenticated", HttpStatus.OK);
        } else {
//            System.out.println("Attempting user creation");
//            System.out.println(codeUserDetailsService.loadUserByUsername(user.getEmail()));
            try {
                codeUserDetailsService.registerNewAccount(user);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity("Invalid email address", HttpStatus.BAD_REQUEST);
            }

            Set<GrantedAuthority> grant = new HashSet<GrantedAuthority>();
            grant.add(new SimpleGrantedAuthority(user.getRole().toString()));
            UsernamePasswordAuthenticationToken authrequest = new UsernamePasswordAuthenticationToken(user.getEmail(), null, grant);
            SecurityContextHolder.getContext().setAuthentication(authrequest);
//            System.out.println("Created account for login controller");
            return new ResponseEntity("Created, authenticated", HttpStatus.OK);
        }
        //return new ResponseEntity("Invalid data", HttpStatus.BAD_REQUEST);

    }


    @RequestMapping(value = "/conf/user", method = RequestMethod.DELETE)
    public void logout(HttpSession session) {
        session.invalidate();
    }


    @RequestMapping(value = "/conf/user/test", method = RequestMethod.GET)
    public ResponseEntity test(Authentication authentication) {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }

        return new ResponseEntity("Ok", HttpStatus.OK);
    }


}
