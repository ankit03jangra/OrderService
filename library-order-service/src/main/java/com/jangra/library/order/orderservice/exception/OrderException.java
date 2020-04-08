package com.jangra.library.order.orderservice.exception;

import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class OrderException {
	
	JSONParser parser = new JSONParser();
	
	   @ExceptionHandler(ResponseStatusException.class)
		public ResponseEntity<OrderValidationError> responseStatusError(ResponseStatusException ex) {
	        String message = ex.getReason();
	        OrderValidationError error= new OrderValidationError("Invalid Request Body", message);
	        return new ResponseEntity<OrderValidationError>(error,HttpStatus.BAD_REQUEST);

		}
	  
}
