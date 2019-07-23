package com.springboot.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;

public class RestResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		org.apache.camel.Message in = exchange.getIn();
		String msg = in.getBody(String.class);
		int status = in.getHeader(exchange.HTTP_RESPONSE_CODE, Integer.class);

		if (status == Integer.parseInt(String.valueOf(HttpStatus.OK))) {
			exchange.getOut().setBody(msg);
		} else {
			throw new Exception("Response failed, status is not ok");
		}

	}
}
