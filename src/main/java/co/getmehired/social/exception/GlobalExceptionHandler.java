package co.getmehired.social.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleGlobalExceptions(Exception ex, WebRequest request) {
    	LOG.error("Unknown exception.", ex);
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), "Internal Server Error", null);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleNotFoundExceptions(Exception ex, WebRequest request) {
    	LOG.error(ex.getMessage(), ex);
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage(), null);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidInputException.class})
    public ResponseEntity<?> handleInvalidInputException(Exception ex, WebRequest request) {
    	LOG.error(ex.getMessage(), ex);
    	ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, LocalDateTime.now(), ex.getMessage(), null);
    	return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidIdTokenException.class})
    public ResponseEntity<?> handleInvalidIdTokenException(Exception ex, WebRequest request) {
    	LOG.error(ex.getMessage(), ex);
    	ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, LocalDateTime.now(), ex.getMessage(), null);
    	return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

}
