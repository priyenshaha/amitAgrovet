package com.amitagrovet.service;

import com.amitagrovet.entity.Order;
import com.amitagrovet.entity.enums.OrderType;

public interface ReportService {
	String generateBillPdf(Order order);
	
	String generateMonthlyReport(OrderType type, String month);
}
