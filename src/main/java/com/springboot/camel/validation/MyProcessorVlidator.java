package com.springboot.camel.validation;

import java.util.Currency;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("myProcessorVlidator")
public class MyProcessorVlidator {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	ValidationConstants validatorConstants = new ValidationConstants();

	@NotEmpty
	private String requestType;
	
	@NotEmpty
	private String TRN;
	
	@NotBlank
	private String name;
	
	@NotEmpty
	private String formatType;
	
	@NotEmpty
	private String amount;
	
	@NotEmpty
	private String currency;
	
	@NotEmpty
	private String service;
	
	@NotEmpty
	private String sourceCountry;
	
	@NotEmpty
	private String message;

	Set<Currency> currencies = Currency.getAvailableCurrencies();
	
	
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

	public boolean validate() {

		LOGGER.info("name: " + this.name);
		LOGGER.info("Sourcecountry: " + this.sourceCountry);
		LOGGER.info("service: " + this.service);
		LOGGER.info("message body: " + this.message);

		if (validatorConstants.getList().contains(this.name)
				&& validatorConstants.SOURCECOUNTRYCODE.equals(this.sourceCountry)
				&& validatorConstants.SERVICECODE.equals(this.service)
				&& this.message.toLowerCase().contains(validatorConstants.MESSAGETEXT)) {

			return false;
		} else {
			return true;
		}

	}

}
