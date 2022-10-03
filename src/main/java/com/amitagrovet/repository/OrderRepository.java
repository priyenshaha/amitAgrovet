package com.amitagrovet.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amitagrovet.entity.Order;
import com.amitagrovet.entity.enums.OrderType;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	@Query(nativeQuery = true, value = "select max(bill_number) from orders where order_type='SELL'")
	String getLastRecordBillNumber();

	List<Order> findByOrderTypeAndDateBetween(OrderType type, LocalDate start, LocalDate end, Sort sort);
	
}
