package com.jangra.library.order.orderservice.proxy;

import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

//@FeignClient(name = "library-book-service")
@FeignClient(name = "library-zuul-api")
public interface LibraryBookServiceProxy {
	
	@GetMapping("/library-book-service/LibraryManagement/getBookById/{id}")
	public JSONObject getBookById(@PathVariable Long id);
	
	@PutMapping("/library-book-service/LibraryManagement/orderingBook/{id}")
	public JSONObject orderingBook(@PathVariable int id);
	

	@PutMapping("/library-book-service/LibraryManagement/returningBook/{id}")
	public JSONObject returningBook(@PathVariable int id);

}
