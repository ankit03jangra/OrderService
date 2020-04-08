package com.jangra.library.order.orderservice.controller;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jangra.library.order.orderservice.dao.OrderDao;
import com.jangra.library.order.orderservice.model.BookOrder;
import com.jangra.library.order.orderservice.proxy.LibraryBookServiceProxy;
import com.jangra.library.order.orderservice.proxy.LibraryUserServiceProxy;

@RestController
@RequestMapping("/bookorder")
public class OrderController {
	
	@Autowired
	private OrderDao dao;
	
	@Autowired
	LibraryBookServiceProxy bookProxy;
	
	@Autowired
	LibraryUserServiceProxy userProxy;
	
	@GetMapping("/getAllOrders")
	public List<BookOrder> getAllOrders() {
		return dao.getAllOrders();
	}
	
	
	@PostMapping("/order/requestedBy/{userId}/for/{days}")
	public ResponseEntity<BookOrder> orderBook(@RequestBody JSONObject book, @PathVariable int userId,@PathVariable Integer days){
		Long bookId = Long.decode(book.get("bookId").toString());
		return dao.orderBook(userId,bookId,days);
	}
	
	
	
	@PostMapping("returnBook/bookId/{bookId}/from/orderId/{orderId}")
	public ResponseEntity<BookOrder> returnBook(@PathVariable int bookId,@PathVariable int orderId,@RequestBody JSONObject date){
		return dao.returnBook(bookId,orderId,date);
	}
	
	
	
	
	@PutMapping("updateOder/requestedBy/{userId}/for/{days}")
	public ResponseEntity<BookOrder> updateOrder(@RequestBody JSONObject book, @PathVariable int userId,@PathVariable Integer days){
		Long bookId = Long.decode(book.get("bookId").toString());
		return dao.updateOrder(userId,bookId,days);
	}
	
	
	  
	  @DeleteMapping("/deleteorderRecord/{orderId}")
	  public ResponseEntity<BookOrder> deleteRecord(@PathVariable int orderId){
			return dao.deleteRecord(orderId);
		}
	

}
