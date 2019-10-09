package co.getmehired.social.model;

import co.getmehired.social.validator.UniqueEmailAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Supal on 08-Oct-19.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="users")
public class User {

    @Id
    private String id;

    @NotNull(message = " Must have a name starting with a capital")
    @Pattern(regexp = "^[A-Za-z]*$")
    private String name;

    @UniqueEmailAddress
    private String emailAddress;

}
