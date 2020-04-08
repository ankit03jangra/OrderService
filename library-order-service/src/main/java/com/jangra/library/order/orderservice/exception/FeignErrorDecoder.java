package com.jangra.library.order.orderservice.exception;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class FeignErrorDecoder implements ErrorDecoder{

	@Override
	public Exception decode(String methodKey, Response response) {
		
		String message=null;
		System.out.println("Method" + methodKey);
		JSONParser parser = new JSONParser(); 
		JSONObject json;
		try {
			json = (JSONObject) parser. parse(response.body().toString());
			message = (String) json.get("message");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		switch (response.status()){
        case 400:
                return new ResponseStatusException(HttpStatus.valueOf(response.status()), message); 
        default:
            return new Exception(response.reason());
    } 
	}
	
	

}
