package co.getmehired.social.rest;

import co.getmehired.social.convertor.UserConvertor;
import co.getmehired.social.model.FirebaseUser;
import co.getmehired.social.model.User;
import co.getmehired.social.model.dto.UserDTO;
import co.getmehired.social.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

/**
 * Created by Supal on 08-Oct-19.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private FirebaseUser firebaseUser;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserDTO saveUser(@Validated @RequestBody UserDTO userDto) {

        User user = userService.save(UserConvertor.fromDto(userDto));
        return UserConvertor.toDto(user);

    }

    @GetMapping("/me")
    public UserDTO getUser(@RequestHeader String idToken) {

        if (!isValidUser(idToken)) {
            return null;
        }
        UserDTO userDto = new UserDTO();
        try {
            String firebaseId = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getUid();
            String name = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getName();
            String email = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getEmail();

            if (this.userService.emailExist(email)) {
                User u = userService.getByEmailAddress(email).orElseGet(null);
                userDto = UserConvertor.toDto(u);
            }

        } catch (InterruptedException | ExecutionException e) {
            return null;
        }

        return userDto;
    }

    private boolean isValidUser(String idToken) {

        try {
            String uid = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getUid();
            String name = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getName();
            String email = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getEmail();

            if (!StringUtils.isEmpty(uid) && !StringUtils.isEmpty(email)) {
                FirebaseUser firebaseUser = new FirebaseUser(uid, name, email);
                this.firebaseUser = firebaseUser;
            }

        } catch (InterruptedException | ExecutionException e) {
            return false;
        }

        return true;
    }


}
