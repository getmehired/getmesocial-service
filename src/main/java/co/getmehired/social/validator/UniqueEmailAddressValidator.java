package co.getmehired.social.validator;

import co.getmehired.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailAddressValidator implements ConstraintValidator<UniqueEmailAddress, String> {

	@Autowired
	private UserService userService;

    @Override
    public void initialize(UniqueEmailAddress email) {
    }

	@Override
	public boolean isValid(String email, ConstraintValidatorContext cxt) {
		return !userService.emailExist(email);
	}
	
}
