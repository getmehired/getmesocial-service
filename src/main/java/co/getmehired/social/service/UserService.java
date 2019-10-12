package co.getmehired.social.service;

import co.getmehired.social.model.User;
import co.getmehired.social.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Supal on 08-Oct-19.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean emailExist(String email) {
        boolean exist = userRepository.existsByEmailAddress(email);
        return exist;
    }

    public Optional<User> getByEmailAddress(String email) {
        return  userRepository.findByEmailAddress(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
