package mediscreen.clientUi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feign.FeignException;

/**
 * Allow handling of exception in a global way, allow customisation of default
 * spring handling of some exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handle feign exception.
	 *
	 * @param ex the feign exception
	 * @return the response entity to return to the requestor
	 */
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Object> handleFeignException(FeignException ex) {
		return new ResponseEntity<>(ex.contentUTF8(), HttpStatus.resolve(ex.status()));
	}
}
