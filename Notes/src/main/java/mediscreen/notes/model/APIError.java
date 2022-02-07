package mediscreen.notes.model;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/*
 * Data transfer object for custom response in case of error.
 */
@Getter
@ToString
@AllArgsConstructor
public class APIError {

	private String timestamp;
	// message relative to the error if precision is needed use errors field
	private String message;
	// Should contain data only if message isnt sufficient
	private Map<String, String> errors;
	private HttpStatus status;
	private String path;
}