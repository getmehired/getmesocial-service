package co.getmehired.social.repository;

import co.getmehired.social.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlbumRepository extends MongoRepository<Album, String> {

    List<Album> findAllByCreatedBy(String createdBy);
}
