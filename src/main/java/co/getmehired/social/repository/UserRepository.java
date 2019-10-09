package co.getmehired.social.repository;

import co.getmehired.social.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
	
	Optional<User> findByName(String name);

	Optional<User> findByEmailAddress(String email);

	boolean existsByEmailAddress(String email);

}
