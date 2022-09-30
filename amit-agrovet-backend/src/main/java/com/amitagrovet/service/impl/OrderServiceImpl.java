package com.amitagrovet.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.amitagrovet.entity.Order;
import com.amitagrovet.entity.enums.OrderType;
import com.amitagrovet.repository.OrderRepository;
import com.amitagrovet.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepo;
	
	@Override
	public String getLatestBillNumber() {
		return orderRepo.getLastRecordBillNumber();
	}

	@Override
	public Order addOrEditOrder(Order order) {
		
		return orderRepo.save(order);
	}

	@Override
	public List<Order> getAllOrdersByType(OrderType type, String month) {
		int yearValue = Integer.valueOf(month.split("-")[0]);
		int monthValue = Integer.valueOf(month.split("-")[1]);
		LocalDate start = LocalDate.of(yearValue, monthValue,1);
		LocalDate end = LocalDate.of(Integer.valueOf(month.split("-")[0]), Integer.valueOf(month.split("-")[1]), YearMonth.of(yearValue, monthValue).lengthOfMonth());
		return orderRepo.findByOrderTypeAndDateBetween(type, start, end, Sort.by("date", "billNumber").ascending());
	}

}
