package co.getmehired.social.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailAddressValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmailAddress {
	
    String message() default "Email Address is already being used";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
