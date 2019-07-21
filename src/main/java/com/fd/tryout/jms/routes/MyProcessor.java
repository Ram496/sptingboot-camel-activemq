
package com.fd.tryout.jms.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fd.tryout.jms.validation.MyProcessorVlidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyProcessor implements Processor {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	MyProcessorVlidator myProcessorVlidator = new MyProcessorVlidator();

	@Override
	public void process(Exchange exchange) throws Exception {

		String custom = exchange.getIn().getBody(String.class);

		String requestType = custom.substring(0, 1).trim();
		String TRN = custom.substring(1, 23).trim();
		String name = custom.substring(23, 43).trim();
		String formatType = custom.substring(43, 44).trim();
		String amount = custom.substring(44, 63).trim();

		String currency = custom.substring(63, 66).trim();
		String service = custom.substring(66, 69).trim();
		String sourceCountry = custom.substring(69, 71).trim();
		String message = custom.substring(71, custom.length()).trim();

		myProcessorVlidator.setRequestType(requestType);
		myProcessorVlidator.setTRN(TRN);
		myProcessorVlidator.setName(name);
		myProcessorVlidator.setFormatType(formatType);
		myProcessorVlidator.setAmount(amount);
		myProcessorVlidator.setService(service);
		myProcessorVlidator.setCurrency(currency);
		myProcessorVlidator.setSourceCountry(sourceCountry);
		myProcessorVlidator.setMessage(message);

		Boolean validateFlag = myProcessorVlidator.validate();

		String respnseMessage = null;
		LOGGER.info(String.valueOf(validateFlag));

		if (validateFlag) {
			respnseMessage = "Nothing found, All ok";
		} else {
			respnseMessage = "Suspecious shipment" + "," + "AT" + "," + "ATZ" + "," + myProcessorVlidator.getTRN();
		}

		LOGGER.debug(respnseMessage);

		exchange.getOut().setBody(respnseMessage);

	}

}
