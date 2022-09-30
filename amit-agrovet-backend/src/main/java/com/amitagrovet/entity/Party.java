package com.amitagrovet.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.amitagrovet.entity.enums.PartyType;

@Entity
@Table(name="party")
public class Party extends BaseEntity{
	
	@NotBlank(message = "Name is mandatory")
	@Column(name = "name", length = 150)
	String name;
	
	@Column(name = "address", length = 255)
	String address;
	
	@Column(name = "state_code", length = 5)
	String stateCode;
	
	@Column(name = "gst_number", length = 20)
	String gstNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	PartyType type;
	
	@Column(precision = 10, scale = 2)
	BigDecimal price;
	
	@Column(name = "is_deleted", columnDefinition = "boolean default false")
	Boolean isDeleted = false;

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getGstNumber() {
		return gstNumber;
	}

	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}

	public PartyType getType() {
		return type;
	}

	public void setType(PartyType type) {
		this.type = type;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Party [name=" + name + ", address=" + address + ", stateCode=" + stateCode + ", gstNumber=" + gstNumber
				+ ", type=" + type + ", price=" + price + ", isDeleted=" + isDeleted + "]";
	}
	
}
