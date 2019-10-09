package co.getmehired.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseUser {
	
	private String uid;
	
	private String name;
	
	private String email;

}
