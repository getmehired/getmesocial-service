package co.getmehired.social.service;

import co.getmehired.social.model.Comment;
import co.getmehired.social.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Supal on 08-Oct-19.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPhotoId(String photoId) {
        return commentRepository.findAllByPhotoId(photoId);
    }

}
