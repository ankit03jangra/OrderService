package com.jangra.library.order.orderservice.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.jangra.library.order.orderservice.model.BookOrder;
import com.jangra.library.order.orderservice.proxy.LibraryBookServiceProxy;
import com.jangra.library.order.orderservice.proxy.LibraryUserServiceProxy;
import com.jangra.library.order.orderservice.repository.OrderRepository;

@Service
public class OrderDao {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	LibraryBookServiceProxy bookProxy;
	
	@Autowired
	LibraryUserServiceProxy userProxy;
	
	int fine=0;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	Date date;
	Date returnDate;
	
	public List<BookOrder> getAllOrders() {
		return repository.findAll();
	}

	public ResponseEntity<BookOrder> orderBook(int userId, Long bookId, Integer days){
		
		JSONObject userStatus = userProxy.getUserById(userId);
		JSONObject bookStatus = bookProxy.getBookById(bookId);
		Boolean active = (Boolean) userStatus.get("isActive");
		Boolean available = (Boolean)bookStatus.get("available");

		int total = (int)bookStatus.get("rent")*days;
		
		if(active==false) {
		  throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"User " +userId+" is blocked"); 
		}
		if(available==false) {
		  throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Book " +bookId+" is not available right now."); 
		}
		
		Date iDate = new Date();
		String issueDate = sdf.format(iDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(iDate);
		cal.add(Calendar.DAY_OF_MONTH,days);
		Date rDate = cal.getTime();
		String returnDate = sdf.format(rDate);
		BookOrder order = new BookOrder(bookId, issueDate, returnDate, days,total, userId,"Issued.");
		
		bookProxy.orderingBook(bookId.intValue());
		repository.save(order);
		return new ResponseEntity<BookOrder>(order,HttpStatus.OK);
	}

	
	
	
	public ResponseEntity<BookOrder> returnBook(int bookId,int orderId, JSONObject rDate) {
		
		JSONObject bookStatus = bookProxy.getBookById((long)bookId);
		Boolean available = (Boolean)bookStatus.get("available");
		if(available==true) {
		  throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Book " +bookId+" is not valid."); 
		}
		
		String dateString = (String)rDate.get("date");
		BookOrder order =  repository.findByBookIdAndOrderId((long)bookId,(long)orderId);
		if(order==null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"OrderId is not correct");
		
		try {
			date = sdf.parse(dateString);
			returnDate = sdf.parse(order.getReturnDate());
		} catch (ParseException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"Parsing exception");
		}
		long diff = date.getTime() - returnDate.getTime();
		int daysBetween = (int)(diff / (1000*60*60*24));
		
		if(daysBetween>0) {
			fine =(int)bookStatus.get("rent")*2*daysBetween;
			order.setStatus("Returned with fine " + fine);
		}
		else {
			order.setStatus("Returned without due.");			
		}
		
		order.setTotalAmount(order.getTotalAmount()+fine);
		order.setReturnDate(dateString);
		bookProxy.returningBook(bookId);
		repository.save(order);
		return new ResponseEntity<BookOrder>(order,HttpStatus.OK);
	}

	public ResponseEntity<BookOrder> updateOrder(int userId, Long bookId, Integer days) {
		
		BookOrder record= repository.findByExternalQuery(userId,bookId,"Issued.");
		if(record==null)
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"User "+userId+" and Book "+bookId+" combination is not correct");
		
		//Date iDate = new Date();
		try {
			date = sdf.parse(record.getIssueDate());
		} catch (ParseException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,"Parsing exception");
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH,days);
		Date rDate = cal.getTime();
		String returnDate = sdf.format(rDate);
		
		
		int total = record.getTotalAmount()/record.getNumberOfDays()*days;
		record.setStatus(record.getStatus());
		record.setReturnDate(returnDate);
		record.setTotalAmount(total);
		repository.save(record);
		return new ResponseEntity<BookOrder>(record,HttpStatus.OK);
	
	}

	public ResponseEntity<BookOrder> deleteRecord(int orderId) {
		Optional<BookOrder> record = repository.findById((long)orderId);
		if(record.isPresent())	
			repository.deleteById((long)orderId);
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Order "+orderId+" is not valid");
		return new ResponseEntity<BookOrder>(record.get(),HttpStatus.OK);
	}

}
