package co.getmehired.social.rest;

import co.getmehired.social.convertor.CommentConvertor;
import co.getmehired.social.convertor.PhotoConvertor;
import co.getmehired.social.model.Comment;
import co.getmehired.social.model.FirebaseUser;
import co.getmehired.social.model.Photo;
import co.getmehired.social.model.dto.CommentDTO;
import co.getmehired.social.model.dto.PhotoDTO;
import co.getmehired.social.service.CommentService;
import co.getmehired.social.service.PhotoService;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Supal on 08-Oct-19.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/photos")
public class PhotoResource {

    private FirebaseUser firebaseUser;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public PhotoDTO savePhoto(@RequestHeader String idToken, @Validated @RequestBody PhotoDTO photoDTO) {

        if (!isValidUser(idToken)) {
            return null;
        }

        Photo photo = PhotoConvertor.fromDto(photoDTO);
        photo.setCreatedBy(firebaseUser.getEmail());
        photoService.savePhoto(photo);
        return PhotoConvertor.toDto(photo);
    }

    @PostMapping("/comments")
    public CommentDTO saveComment(@RequestHeader String idToken,
                                  @Validated @RequestBody CommentDTO commentDTO) {

        if (!isValidUser(idToken)) {
            return null;
        }

        Comment comment = CommentConvertor.fromDto(commentDTO);
        comment.setCreatedBy(firebaseUser.getEmail());
        comment.setComment(commentDTO.getComment());
        comment.setPhotoId(commentDTO.getPhotoId());
        comment.setDateCreated(new Date());
        Comment savedComment = commentService.save(comment);
        return CommentConvertor.toDto(savedComment);
    }

    @GetMapping
    public List<PhotoDTO> getPhotos(@RequestHeader String idToken) {

        if (!isValidUser(idToken)) {
            return null;
        }

        List<Photo> photos = photoService.getAllPhotos();
        List<PhotoDTO> photoDTOs = new ArrayList<>();
        for(Photo Photo:photos) {
            photoDTOs.add(PhotoConvertor.toDto(Photo));
        }

        return photoDTOs;
    }

    @GetMapping("/{id}")
    public PhotoDTO getPhoto(@RequestHeader String idToken,
                                   @PathVariable(name = "id") String id) {

        if (!isValidUser(idToken)) {
            return null;
        }

        Photo photo = photoService.getPhotoById(id);
        return PhotoConvertor.toDto(photo);
    }

    @GetMapping("/{id}/comments")
    public List<CommentDTO> getPhotoComments(@RequestHeader String idToken,
                             @PathVariable(name = "id") String id) {

        if (!isValidUser(idToken)) {
            return null;
        }

        List<Comment> comments = commentService.getCommentsByPhotoId(id);

        List<CommentDTO> commentDTOs = new ArrayList<>();
        for(Comment comment:comments) {
            commentDTOs.add(CommentConvertor.toDto(comment));
        }
        return commentDTOs;
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
