package mediscreen.patientInfo.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Could not find ressource");
		body.put("status", HttpStatus.NOT_FOUND);
		body.put("path", request.getDescription(false));

		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PatientInfoAlreadyExistException.class)
	public ResponseEntity<Object> handleNoSuchElementException(PatientInfoAlreadyExistException ex,
			WebRequest request) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Ressource already exist");
		body.put("status", HttpStatus.SEE_OTHER);
		body.put("path", request.getDescription(false));

		return new ResponseEntity<>(body, HttpStatus.SEE_OTHER);
	}

}
