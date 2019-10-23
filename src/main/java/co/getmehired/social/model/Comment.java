package co.getmehired.social.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Supal on 08-Oct-19.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="comments")
public class Comment {

    @Id
    private String id;

    private String comment;

    private Date dateCreated;

    private User createdBy;

}
