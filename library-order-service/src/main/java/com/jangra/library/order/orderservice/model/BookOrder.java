package com.jangra.library.order.orderservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BookOrder {
	
	@Id
	@GeneratedValue
	private Long orderId;
	private Long bookId;
	private int userId;
	private String issueDate;
	private String returnDate;
	private Integer numberOfDays;
	private Integer totalAmount;
	
	private String status;
	
	
	public BookOrder() {
		super();
	}

	public BookOrder(Long bookId, String issueDate, String returnDate, Integer numberOfDays,
			Integer totalAmount, int userId,String status) {
		super();
		this.bookId = bookId;
		this.issueDate = issueDate;
		this.returnDate = returnDate;
		this.numberOfDays = numberOfDays;
		this.totalAmount = totalAmount;
		this.userId = userId;
		this.status =status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public Integer getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

	
}
