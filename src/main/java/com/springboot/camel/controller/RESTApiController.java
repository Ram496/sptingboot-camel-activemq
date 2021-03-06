package com.springboot.camel.controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.camel.model.TransactionMessage;

@RestController
public class RESTApiController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public ResponseEntity<String> updateTransactionStatus(Map<String, TransactionMessage> map) throws IOException {

		//final String json = org.apache.commons.io.IOUtils.toString(request.getInputStream());

		LOGGER.info("Response recevied from Camel : " + map);
		
		return new ResponseEntity<String>("Got it, Thanks",HttpStatus.OK);
	}

	

}
