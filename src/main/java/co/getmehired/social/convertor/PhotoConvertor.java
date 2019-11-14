package co.getmehired.social.convertor;

import co.getmehired.social.model.Photo;
import co.getmehired.social.model.dto.PhotoDTO;

public class PhotoConvertor {
  private PhotoConvertor() {
    throw new IllegalStateException("Utility class");
  }

  public static PhotoDTO toDto(Photo photo) {
    // TODO populate other objects
    PhotoDTO dto = new PhotoDTO();
    dto.setId(photo.getId());
    dto.setPhotoUrl(photo.getPhotoUrl());
    dto.setAlbumId(photo.getAlbumId());
    dto.setThumbnailUrl(photo.getThumbnailUrl());
    dto.setDateCreated(photo.getDateCreated());
    dto.setCreatedBy(photo.getCreatedBy());
    return dto;
  }

  public static Photo fromDto(PhotoDTO dto) {
    // TODO populate other objects
    Photo photo = new Photo();
    photo.setPhotoUrl(dto.getPhotoUrl());
    photo.setAlbumId(dto.getAlbumId());
    photo.setThumbnailUrl(dto.getThumbnailUrl());
    return photo;
  }

}
