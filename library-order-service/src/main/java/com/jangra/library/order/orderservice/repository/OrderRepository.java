package com.jangra.library.order.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jangra.library.order.orderservice.model.BookOrder;

@Repository
public interface OrderRepository extends JpaRepository<BookOrder, Long> {
	
	public BookOrder findByBookIdAndOrderId(Long bookId,Long orderId);

	@Query(value = "select * from book_order where user_id=?1 and book_id=?2 and status =?3",nativeQuery = true)
	public BookOrder findByExternalQuery(int userId, Long bookId,String issued);

}
