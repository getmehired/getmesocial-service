package co.getmehired.social.rest;

import co.getmehired.social.convertor.PhotoConvertor;
import co.getmehired.social.model.Photo;
import co.getmehired.social.model.dto.PhotoDTO;
import co.getmehired.social.service.PhotoService;
import co.getmehired.social.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Supal on 08-Oct-19.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/photos")
public class PhotoResource {


    @Autowired
    private UserService userService;

    @Autowired
    private PhotoService photoService;

    @PostMapping
    public PhotoDTO savePhoto(@Validated @RequestBody PhotoDTO photoDTO) {
        Photo photo = photoService.savePhoto(PhotoConvertor.fromDto(photoDTO));
        return PhotoConvertor.toDto(photo);
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

    private boolean isValidUser(String idToken) {

        try {
            String uid = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getUid();
            String name = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getName();
            String email = FirebaseAuth.getInstance().verifyIdTokenAsync(idToken).get().getEmail();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }

        return true;
    }


}
