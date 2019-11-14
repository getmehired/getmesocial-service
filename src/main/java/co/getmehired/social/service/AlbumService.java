package co.getmehired.social.service;

import co.getmehired.social.model.Album;
import co.getmehired.social.model.Photo;
import co.getmehired.social.repository.AlbumRepository;
import co.getmehired.social.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Supal on 08-Oct-19.
 */
@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private PhotoRepository photoRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public List<Album> getAlbumsByUser(String createdBy) {
        return albumRepository.findAllByCreatedBy(createdBy);
    }

    public List<Photo> getPhotos(String albumId) {
        return photoRepository.findAllByAlbumId(albumId);
    }


    public Album saveAlbum(Album album) {
        album.setCreationDate(new Date());
        return albumRepository.save(album);
    }

}
