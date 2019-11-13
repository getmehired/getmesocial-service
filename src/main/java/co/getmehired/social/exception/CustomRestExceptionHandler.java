package co.getmehired.social.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		
		List<ApiSubError> errors = new ArrayList<>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(new ApiValidationError(error.getObjectName(), error.getField(), error.getRejectedValue(), error.getDefaultMessage()));
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(new ApiValidationError(error.getObjectName(), null, null, error.getDefaultMessage()));
		}

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, LocalDateTime.now(), "Invalid inputs", errors);
		return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(
	  NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	    String message = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
	 
	    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, LocalDateTime.now(), message, null);
	    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

}

