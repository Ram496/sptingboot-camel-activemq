package com.springboot.camel.validation;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.camel.model.InputData;
import com.springboot.camel.model.TransactionMessage;

@Component
public class InputMessageValidator {

	private static final Logger log = LoggerFactory.getLogger(InputMessageValidator.class);

	@Autowired
	InputData input;

	@Autowired
	ValidationConstants CONSTANTS;

	@Autowired
	TransactionMessage messageBody;

	public void saveData(Exchange exchange) {

		log.info("exchange: " + exchange.getIn().getBody());

		// Getting the exchange message/incoming message and storing in string
		String custom = exchange.getIn().getBody(String.class);

		// getting data from local string variable and extracting data as per given
		// lengths

		input.setRequestType(custom.substring(CONSTANTS.REQUESTTYPE_MIN, CONSTANTS.REQUESTTYPE_MAX));
		input.setTRN(custom.substring(CONSTANTS.TRN_MIN, CONSTANTS.TRN_MAX));
		input.setName(custom.substring(CONSTANTS.NAME_MIN, CONSTANTS.NAME_MAX));
		input.setFormatType(custom.substring(CONSTANTS.FORMATTYPE_MIN, CONSTANTS.FORMATTYPE_MAX));
		input.setAmount(custom.substring(CONSTANTS.AMOUNT_MIN, CONSTANTS.AMOUNT_MAX));
		input.setCurrency(custom.substring(CONSTANTS.CURRENCY_MIN, CONSTANTS.CURRENCY_MAX));
		input.setService(custom.substring(CONSTANTS.SERVICE_MIN, CONSTANTS.SERVICE_MAX));
		input.setSourceCountry(custom.substring(CONSTANTS.SOURCECOUNTRY_MIN, CONSTANTS.SOURCECOUNTRY_MAX));
		input.setMessage(custom.substring(CONSTANTS.MESSAGE_MIN, custom.length()));

	}

	public void validateData() throws Exception {

		log.info("bean validation..started");
		log.info("bean date: " + input);

		Boolean validationFalg = true;

		if (input.getRequestType().length() != 1 || input.getTRN().length() != 22 || input.getName().length() != 20
				|| input.getFormatType().length() != 1 || input.getAmount().length() != 19
				|| input.getCurrency().length() != 3 || input.getService().length() != 3
				|| input.getSourceCountry().length() != 2 || input.getMessage().isEmpty()) {

			validationFalg = false;

		} else if (!input.getRequestType().equals("G") || !input.getFormatType().equals("U")) {

			validationFalg = false;

		} else if (!CONSTANTS.currencies.stream().filter(o -> o.getCurrencyCode().equals(input.getCurrency()))
				.findFirst().isPresent()) {

			validationFalg = false;

		} else if (!CONSTANTS.getService().contains(input.getService())) {

			validationFalg = false;

		} else if (!input.getAmount().matches("^\\d{15}+\\.\\d{3}$")) {

			validationFalg = false;

		}

		if (validationFalg == false) {
			throw new Exception("data is invalid");
		}

	}

	public void validateTransaction(Exchange exchange) {

		Boolean validateFlag = true;

		if (CONSTANTS.getList().contains(input.getName().trim())
				&& CONSTANTS.SOURCECOUNTRYCODE.equals(input.getSourceCountry())
				&& CONSTANTS.SERVICECODE.equals(input.getService())
				&& input.getMessage().toLowerCase().contains(CONSTANTS.MESSAGETEXT)) {

			validateFlag = false;
		}

		if (validateFlag) {
			messageBody.setMessage("Nothing found, All ok");
		} else {
			messageBody.setMessage("Suspecious shipment" + "," + "AT" + "," + "ATZ" + "," + input.getTRN());
		}

		log.debug("Response message: " + messageBody.getMessage());

		/*
		 * update the message body with response after processing as per criteria
		 * converted message, will be moved to end points
		 */

		messageBody.setId(exchange.getExchangeId());

		exchange.getOut().setBody(messageBody);

		log.info("exchange data: " + exchange.getOut().getBody());

	}

}
