package com.amitagrovet.service;

import com.amitagrovet.entity.Order;

public interface ReportService {
	String generateReportPdf(Order order);
}
