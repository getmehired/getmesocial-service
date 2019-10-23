package co.getmehired.social.convertor;

import co.getmehired.social.model.User;
import co.getmehired.social.model.dto.UserDTO;

public class UserConvertor {
  private UserConvertor() {
    throw new IllegalStateException("Utility class");
  }

  public static UserDTO toDto(User u) {
    return new UserDTO(u.getId(), u.getName(), u.getEmailAddress(), u.getProfilePhotoUrl());
  }

  public static User fromDto(UserDTO udto) {
    return new User(udto.getId(), udto.getName(), udto.getEmailAddress(), udto.getProfilePhotoUrl());
  }

}
