package com.springboot.camel.model;

import org.springframework.stereotype.Component;

@Component
public class InputData {

	private String requestType;
	private String TRN;
	private String name;
	private String formatType;
	private String amount;
	private String currency;
	private String service;
	private String sourceCountry;
	private String message;
	
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getTRN() {
		return TRN;
	}
	public void setTRN(String tRN) {
		TRN = tRN;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFormatType() {
		return formatType;
	}
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSourceCountry() {
		return sourceCountry;
	}
	public void setSourceCountry(String sourceCountry) {
		this.sourceCountry = sourceCountry;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "InputData [requestType=" + requestType + ", TRN=" + TRN + ", name=" + name + ", formatType="
				+ formatType + ", amount=" + amount + ", currency=" + currency + ", service=" + service
				+ ", sourceCountry=" + sourceCountry + ", message=" + message + "]";
	}

	
	
	
	
}
