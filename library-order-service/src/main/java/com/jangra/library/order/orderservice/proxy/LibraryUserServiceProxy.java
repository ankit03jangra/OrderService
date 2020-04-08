package com.jangra.library.order.orderservice.proxy;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="library-user-service" , url = "http://localhost:8080/LibraryManagement")
public interface LibraryUserServiceProxy {

	@GetMapping("/getUserById/{id}")
	public JSONObject getUserById(@PathVariable int id);
}
