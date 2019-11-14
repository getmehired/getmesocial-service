package co.getmehired.social.service;

import co.getmehired.social.model.Photo;
import co.getmehired.social.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Supal on 08-Oct-19.
 */
@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    public Photo savePhoto(Photo photo) {
        photo.setDateCreated(new Date());
        return photoRepository.save(photo);
    }

}
