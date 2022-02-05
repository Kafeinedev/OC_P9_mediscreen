package mediscreen.clientUi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import feign.FeignException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Object> handleFeignException(FeignException ex) {
		return new ResponseEntity<>(ex.contentUTF8(), HttpStatus.resolve(ex.status()));
	}
}
