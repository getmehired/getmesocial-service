package co.getmehired.social.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class CommonDataResource {

	@GetMapping("/api/hello")
	public String getTimezones() {
		return "Hello World";
	}

}
