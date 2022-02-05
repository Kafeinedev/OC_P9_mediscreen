package mediscreen.patientInfo.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		Map<String, Object> body = new HashMap<>();
		Map<String, String> bindingResult = new HashMap<>();
		for (FieldError err : ex.getBindingResult().getFieldErrors()) {
			bindingResult.put(err.getField(), err.getDefaultMessage());
		}

		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Invalid fields");
		body.put("errors", bindingResult);
		body.put("status", HttpStatus.BAD_REQUEST);
		body.put("path", request.getDescription(false));

		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}
}
