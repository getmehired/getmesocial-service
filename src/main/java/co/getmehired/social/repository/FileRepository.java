package co.getmehired.social.repository;

import co.getmehired.social.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRepository extends MongoRepository<File, String> {

	Optional<File> findByFileId(String fileId);

}
