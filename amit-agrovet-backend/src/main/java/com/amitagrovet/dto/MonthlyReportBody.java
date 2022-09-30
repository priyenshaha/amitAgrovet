package com.amitagrovet.dto;

import com.amitagrovet.entity.enums.OrderType;

public class MonthlyReportBody {
	OrderType type;
	String month;
	public OrderType getType() {
		return type;
	}
	public void setType(OrderType type) {
		this.type = type;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	@Override
	public String toString() {
		return "MonthlyReportBody [type=" + type + ", month=" + month + "]";
	}
	
	
}
