package mediscreen.clientUi.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mediscreen.clientUi.bean.APIError;

/*
 * Util class for manipulating APIError data transfer object.
 */
public class APIErrorUtil {

	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * Extract the error field from a json object representing an APIError.
	 *
	 * @param rawAPIError the string formatted in json that containt the APIError
	 * @return the error field from the APIError
	 * @throws JsonMappingException    the json mapping exception
	 * @throws JsonProcessingException the json processing exception
	 */
	public Map<String, String> extractError(String rawAPIError) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(rawAPIError, APIError.class).getErrors();
	}
}
