package co.getmehired.social.rest;

import co.getmehired.social.convertor.AlbumConvertor;
import co.getmehired.social.convertor.UserConvertor;
import co.getmehired.social.exception.InvalidIdTokenException;
import co.getmehired.social.model.Album;
import co.getmehired.social.model.FirebaseUser;
import co.getmehired.social.model.User;
import co.getmehired.social.model.dto.AlbumDTO;
import co.getmehired.social.model.dto.UserDTO;
import co.getmehired.social.service.AlbumService;
import co.getmehired.social.service.UserService;
import co.getmehired.social.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Supal on 08-Oct-19.
 */

@CrossOrigin
@RestController
@RequestMapping("/api/albums")
public class AlbumResource {

    private FirebaseUser firebaseUser;

    @Autowired
    private AlbumService albumService;

    @GetMapping("/all")
    public List<AlbumDTO> getAlbums(@RequestHeader String idToken) {

        if (!isValidUser(idToken)) {
            throw new InvalidIdTokenException("Invalid Id Token");
        }

        List<Album> albums = albumService.getAllAlbums();
        List<AlbumDTO> albumDTOs = new ArrayList<>();
        for(Album album:albums) {
            albumDTOs.add(AlbumConvertor.toDto(album));
        }

        return albumDTOs;
    }

    @GetMapping
    public List<AlbumDTO> getMyAlbums(@RequestHeader String idToken) {

        if (!isValidUser(idToken)) {
            throw new InvalidIdTokenException("Invalid Id Token");
        }

        List<Album> albums = albumService.getAlbumsByUser(firebaseUser.getEmail());
        List<AlbumDTO> albumDTOs = new ArrayList<>();
        for(Album album:albums) {
            albumDTOs.add(AlbumConvertor.toDto(album));
        }

        return albumDTOs;
    }

    @PostMapping
    public AlbumDTO saveAlbum(@RequestBody AlbumDTO albumDTO, @RequestHeader String idToken) {

        if (!isValidUser(idToken)) {
            throw new InvalidIdTokenException("Invalid Id Token");
        }

        Album album = AlbumConvertor.fromDto(albumDTO);
        album.setCreatedBy(firebaseUser.getEmail());
        album = albumService.saveAlbum(album);
        return AlbumConvertor.toDto(album);
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
