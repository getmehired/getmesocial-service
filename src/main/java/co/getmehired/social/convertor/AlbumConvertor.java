package co.getmehired.social.convertor;

import co.getmehired.social.model.Album;
import co.getmehired.social.model.dto.AlbumDTO;

public class AlbumConvertor {
  private AlbumConvertor() {
    throw new IllegalStateException("Utility class");
  }

  public static AlbumDTO toDto(Album album) {
    // TODO populate other objects
    AlbumDTO dto = new AlbumDTO();
    dto.setId(album.getId());
    dto.setTitle(album.getTitle());
    dto.setCoverPhotoUrl(album.getCoverPhotoUrl());
    return dto;
  }

  public static Album fromDto(AlbumDTO dto) {
    // TODO populate other objects
    Album album = new Album();
    album.setTitle(dto.getTitle());
    return album;
  }

}
