package co.getmehired.social.convertor;

import co.getmehired.social.model.Comment;
import co.getmehired.social.model.dto.CommentDTO;

public class CommentConvertor {
  private CommentConvertor() {
    throw new IllegalStateException("Utility class");
  }

  public static CommentDTO toDto(Comment comment) {
    // TODO populate other objects
    CommentDTO dto = new CommentDTO();
    return dto;
  }

  public static Comment fromDto(CommentDTO dto) {
    // TODO populate other objects
    Comment comment = new Comment();
    return comment;
  }

}
