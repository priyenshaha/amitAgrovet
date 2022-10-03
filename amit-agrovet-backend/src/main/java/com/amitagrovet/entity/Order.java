package com.amitagrovet.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.amitagrovet.entity.enums.OrderType;

@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = {"party_id", "bill_number", "date"}))
public class Order extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "party_id")
	private Party party;

	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	@Column(name="bill_number")
	private Integer billNumber;
	private String vehicle;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDate date;

	private BigDecimal quantity;
	
	private Integer adjustedAmount;
	
	private String productName;
	private String hsnCode;
	
	public Party getParty() {
		return party;
	}
	public void setParty(Party party) {
		this.party = party;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public Integer getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(Integer billNumber) {
		this.billNumber = billNumber;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	
	public Integer getAdjustedAmount() {
		return adjustedAmount;
	}
	public void setAdjustedAmount(Integer adjustedAmount) {
		this.adjustedAmount = adjustedAmount;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	@Override
	public String toString() {
		return "Order [party=" + party + ", orderType=" + orderType + ", billNumber=" + billNumber + ", vehicle="
				+ vehicle + ", date=" + date + ", quantity=" + quantity + ", adjustedAmount="
				+ adjustedAmount + ", productName=" + productName + ", hsnCode="
				+ hsnCode + "]";
	}
	
	
}
