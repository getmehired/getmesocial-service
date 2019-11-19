package co.getmehired.social.repository;

import co.getmehired.social.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllByPhotoId(String photoId);
}
