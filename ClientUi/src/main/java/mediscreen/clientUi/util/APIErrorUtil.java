package mediscreen.clientUi.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import mediscreen.clientUi.bean.APIError;

public class APIErrorUtil {

	private ObjectMapper mapper = new ObjectMapper();

	public Map<String, String> extractError(String rawAPIError) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(rawAPIError, APIError.class).getErrors();
	}
}
