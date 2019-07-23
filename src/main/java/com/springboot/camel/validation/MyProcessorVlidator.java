package com.springboot.camel.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyProcessorVlidator {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	ValidationConstants validatorConstants = new ValidationConstants();

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
