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
@Table(name="party", uniqueConstraints = @UniqueConstraint(name = "party_uk_1", columnNames = { "gst_number" }) )
public class Party extends BaseEntity{
	
	@NotBlank(message = "Name is mandatory")
	@Column(name = "name", length = 150)
	String name;
	
	@Column(name = "gst_number", length = 20, unique = true)
	String gstNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 10)
	PartyType type;
	
	@Column(precision = 10, scale = 2)
	BigDecimal price;

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
		return "Party [name=" + name + ", gstNumber=" + gstNumber + ", type=" + type + ", price=" + price
				+ "]";
	}
	
}
