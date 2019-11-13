package co.getmehired.social.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by Supal on 08-Oct-19.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDTO {

    @Id
    private String id;

    private String title;

    private String coverPhotoUrl;

    private Date creationDate;

    private String createdBy;

}
