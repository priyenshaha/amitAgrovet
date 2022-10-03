package com.amitagrovet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReportOrderDto {
	LocalDate date;
	Integer billNumber;
	String name;
	String gstNumber;
	String stateCode;
	String hsnCode;
	BigDecimal weight;
	BigDecimal price;
	Integer finalAmount;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Integer getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(Integer billNumber) {
		this.billNumber = billNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(Integer finalAmount) {
		this.finalAmount = finalAmount;
	}
	@Override
	public String toString() {
		return "ReportOrderDto [date=" + date + ", billNumber=" + billNumber + ", name=" + name + ", gstNumber="
				+ gstNumber + ", stateCode=" + stateCode + ", hsnCode=" + hsnCode + ", weight=" + weight + ", price="
				+ price + ", finalAmount=" + finalAmount + "]";
	}
	
	
	
}
