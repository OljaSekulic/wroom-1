package com.wroom.adsservice.producer.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PricelistMessage {

	private Long id;
	private Double pricePerDay;
	private Double priceCDW;
	private Double discount;
//	private boolean deleted;
	private Long userId;
	
	public PricelistMessage() {}
	
	public PricelistMessage( @JsonProperty("id") Long id, 
			@JsonProperty("pricePerDay") Double pricePerDay, 
			@JsonProperty("priceCDW") Double priceCDW, 
			@JsonProperty("discount") Double discount, 
			@JsonProperty("userId") Long userId) {
		super();
		this.id = id;
		this.pricePerDay = pricePerDay;
		this.priceCDW = priceCDW;
		this.discount = discount;
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(Double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public Double getPriceCDW() {
		return priceCDW;
	}

	public void setPriceCDW(Double priceCDW) {
		this.priceCDW = priceCDW;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
}
