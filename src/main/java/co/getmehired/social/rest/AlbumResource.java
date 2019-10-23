package co.getmehired.social.rest;

import co.getmehired.social.convertor.AlbumConvertor;
import co.getmehired.social.model.Album;
import co.getmehired.social.model.dto.AlbumDTO;
import co.getmehired.social.service.AlbumService;
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
@RequestMapping("/api/albums")
public class AlbumResource {


    @Autowired
    private UserService userService;

    @Autowired
    private AlbumService albumService;

    @PostMapping
    public AlbumDTO saveAlbum(@Validated @RequestBody AlbumDTO albumDTO) {
        Album album = albumService.saveAlbum(AlbumConvertor.fromDto(albumDTO));
        return AlbumConvertor.toDto(album);
    }

    @GetMapping
    public List<AlbumDTO> getAlbums(@RequestHeader String idToken) {

        if (!isValidUser(idToken)) {
            return null;
        }

        List<Album> albums = albumService.getAllAlbums();
        List<AlbumDTO> albumDTOs = new ArrayList<>();
        for(Album album:albums) {
            albumDTOs.add(AlbumConvertor.toDto(album));
        }

        return albumDTOs;
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
