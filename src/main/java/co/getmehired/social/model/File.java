package co.getmehired.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="files")
public class File {

	@Id
	private String id;

	@Indexed
	private String fileId;
	
	private String name;
	
	private String path;
	
	private String owner;

}
