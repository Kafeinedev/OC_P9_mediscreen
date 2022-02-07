package mediscreen.notes.exception;

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

import mediscreen.notes.model.APIError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle no such element exception.
	 *
	 * @param ex      the no such element exception ex
	 * @param request the request that lead to the exception
	 * @return the response entity to send back to the requestor
	 */
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {

		APIError error = new APIError(LocalDateTime.now().toString(), "Could not find ressource",
				new HashMap<String, String>(), HttpStatus.NOT_FOUND, request.getDescription(false));

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * PatientInfoAlreadyExistException
	 *
	 * @param ex      the patient info already exist exception ex
	 * @param request the request that lead to the exception
	 * @return the response entity to send back to the requestor
	 */
	@ExceptionHandler(NoteAlreadyExistException.class)
	public ResponseEntity<Object> handlePatientInfoAlreadyExistException(NoteAlreadyExistException ex,
			WebRequest request) {

		APIError error = new APIError(LocalDateTime.now().toString(), "Ressource already exist",
				new HashMap<String, String>(), HttpStatus.SEE_OTHER, request.getDescription(false));

		return new ResponseEntity<>(error, HttpStatus.SEE_OTHER);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {

		Map<String, String> bindingResult = new HashMap<>();
		for (FieldError err : ex.getBindingResult().getFieldErrors()) {
			bindingResult.put(err.getField(), err.getDefaultMessage());
		}

		APIError error = new APIError(LocalDateTime.now().toString(), "Invalid fields", bindingResult,
				HttpStatus.BAD_REQUEST, request.getDescription(false));

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
