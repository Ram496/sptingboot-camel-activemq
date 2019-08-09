package com.springboot.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class RestResponseProcessor implements Processor {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		org.apache.camel.Message in = exchange.getIn();
		String msg = in.getBody(String.class);
		LOGGER.info("response received from REST API, response:"+msg);
		int status = in.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);

		if (status == Integer.parseInt(String.valueOf(HttpStatus.OK))) {
			LOGGER.info("received ok status from REST API:"+status);
			exchange.getOut().setBody(msg);
		} else {
			LOGGER.info("response received from REST API, & status is not ok :"+status);
			throw new Exception("Response failed, status is not ok");
		}

	}
}
