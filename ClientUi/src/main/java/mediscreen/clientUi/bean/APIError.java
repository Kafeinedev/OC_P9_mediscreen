package mediscreen.clientUi.bean;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class APIError {

	private String timestamp;
	private String message;
	private Map<String, String> errors;
	private HttpStatus status;
	private String path;
}
