
package com.springboot.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.springboot.camel.model.TransactionMessage;
import com.springboot.camel.validation.MyProcessorVlidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JMSIncomingMessageProcessor implements Processor {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	MyProcessorVlidator myProcessorVlidator = new MyProcessorVlidator();
	
	TransactionMessage messageBody = new TransactionMessage();
	
	/*
	 * below constants are used to maintain the min and max length of each topic in
	 * message
	 */
	final int REQUESTTYPE_MIN = 0;
	final int REQUESTTYPE_MAX = 1;

	final int TRN_MIN = 1;
	final int TRN_MAX = 23;

	final int NAME_MIN = 23;
	final int NAME_MAX = 43;

	final int FORMATTYPE_MIN = 43;
	final int FORMATTYPE_MAX = 44;

	final int AMOUNT_MIN = 44;
	final int AMOUNT_MAX = 63;

	final int CURRENCY_MIN = 63;
	final int CURRENCY_MAX = 66;

	final int SERVICE_MIN = 66;
	final int SERVICE_MAX = 69;

	final int SOURCECOUNTRY_MIN = 69;
	final int SOURCECOUNTRY_MAX = 71;
	final int MESSAGE_MIN = 71;
	// message max length value is equal to message.length().
	final String MESSAGE_MAX = "";

	@Override
	public void process(Exchange exchange) throws Exception {

		//Getting the exchange message/incoming message and storing in string 
		String custom = exchange.getIn().getBody(String.class);

		// getting data from local string variable and extracting data as per given lengths
		String requestType = custom.substring(REQUESTTYPE_MIN, REQUESTTYPE_MAX).trim();
		String TRN = custom.substring(TRN_MIN, TRN_MAX).trim();
		String name = custom.substring(NAME_MIN, NAME_MAX).trim();
		String formatType = custom.substring(FORMATTYPE_MIN, FORMATTYPE_MAX).trim();
		String amount = custom.substring(AMOUNT_MIN, AMOUNT_MAX).trim();

		String currency = custom.substring(CURRENCY_MIN, CURRENCY_MAX).trim();
		String service = custom.substring(SERVICE_MIN, SERVICE_MAX).trim();
		String sourceCountry = custom.substring(SOURCECOUNTRY_MIN, SOURCECOUNTRY_MAX).trim();
		String message = custom.substring(MESSAGE_MIN, custom.length()).trim();
		
		
		  
		
		
		  

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

		LOGGER.info(String.valueOf("is it valid transaction? : " + validateFlag));

		if (validateFlag) {
			respnseMessage = "Nothing found, All ok";
		} else {
			respnseMessage = "Suspecious shipment" + "," + "AT" + "," + "ATZ" + "," + myProcessorVlidator.getTRN();
		}

		LOGGER.debug("Response message: "+respnseMessage);

		/* update the message body with response after processing as per criteria 
		 * converted message, will be moved to end points 
		 * */
		
		messageBody.setId(exchange.getExchangeId());
		messageBody.setMessage(respnseMessage);
		
		exchange.getOut().setBody(messageBody);
		
		LOGGER.debug("exchange data: "+exchange.getOut().getBody());
	}

}
