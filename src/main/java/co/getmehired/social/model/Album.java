package co.getmehired.social.model;

import co.getmehired.social.validator.UniqueEmailAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Created by Supal on 08-Oct-19.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="albums")
public class Album {

    @Id
    private String id;

    private String title;

    private String coverPhotoUrl;

    private Date creationDate;

    private User createdBy;

}
