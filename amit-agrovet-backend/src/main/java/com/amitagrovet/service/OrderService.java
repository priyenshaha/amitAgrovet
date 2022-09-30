package com.amitagrovet.service;

import java.util.List;

import com.amitagrovet.entity.Order;
import com.amitagrovet.entity.enums.OrderType;

public interface OrderService {

	String getLatestBillNumber();
	
	Order addOrEditOrder(Order order);

	List<Order> getAllOrdersByType(OrderType type, String month);
	
}
