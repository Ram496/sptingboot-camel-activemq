package com.fd.tryout.jms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestConsumer {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public String updateTransactionStatus(HttpServletRequest request) throws IOException {

		final String json = org.apache.commons.io.IOUtils.toString(request.getInputStream());

		LOGGER.info("Response recevied from Camel : " + json);
		LOGGER.info("Response recevied from Camel : " + json);
		return json;
	}

}
