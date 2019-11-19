package co.getmehired.social.convertor;

import co.getmehired.social.model.Comment;
import co.getmehired.social.model.dto.CommentDTO;

public class CommentConvertor {
  private CommentConvertor() {
    throw new IllegalStateException("Utility class");
  }

  public static CommentDTO toDto(Comment comment) {
    CommentDTO dto = new CommentDTO();
    dto.setId(comment.getId());
    dto.setComment(comment.getComment());
    dto.setPhotoId(comment.getPhotoId());
    dto.setDateCreated(comment.getDateCreated());
    dto.setCreatedBy(comment.getCreatedBy());
    return dto;
  }

  public static Comment fromDto(CommentDTO dto) {
    Comment comment = new Comment();
    comment.setPhotoId(dto.getPhotoId());
    comment.setComment(dto.getComment());
    comment.setCreatedBy(dto.getCreatedBy());
    return comment;
  }

}
