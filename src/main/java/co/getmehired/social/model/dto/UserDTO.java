package co.getmehired.social.model.dto;

import co.getmehired.social.validator.UniqueEmailAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private String id;
	
	private String name;

	@UniqueEmailAddress
	private String emailAddress;

	private String profilePhotoUrl;
}
