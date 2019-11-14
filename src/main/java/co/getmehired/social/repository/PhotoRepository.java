package co.getmehired.social.repository;

import co.getmehired.social.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PhotoRepository extends MongoRepository<Photo, String> {

    List<Photo> findAllByAlbumId(String albumId);
}
