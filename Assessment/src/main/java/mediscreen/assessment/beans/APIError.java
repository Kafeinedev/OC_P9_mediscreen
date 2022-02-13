package mediscreen.assessment.beans;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Data transfer object for api error
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class APIError {

	private String timestamp;
	// message relative to the error if precision is needed use errors field
	private String message;
	// Should contain data only if message isnt sufficient
	private Map<String, String> errors;
	private HttpStatus status;
	private String path;
}
