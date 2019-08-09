package com.springboot.camel.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

import com.springboot.camel.model.TransactionMessage;

import ch.qos.logback.core.util.ContentTypeUtil;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RESTApiControllerTest {

	/*
	 * @Autowired private ProducerTemplate producerTemplate;
	 * 
	 * 
	 */
	
	@Autowired 
	private CamelContext context;

	/*@Autowired
		private TestRestTemplate restTemplate;
	*/
	
	@Produce(uri = "mock:direct:in")
	protected ProducerTemplate testProducer;
	
	@Test
	public void restApiTest() throws InterruptedException {
		TransactionMessage message = new TransactionMessage();
		message.setId("12-3-4-3-2223");
		message.setMessage("suspecious transaction");
		
		
		    
			testProducer.requestBody(message);
		    
		    MockEndpoint mockEndpoint = context.getEndpoint("mock:http://localhost:8090/status",MockEndpoint.class);
		    System.out.println("mock end point name: "+mockEndpoint.getName());
		    mockEndpoint.expectedMessageCount(1);
		    mockEndpoint.assertIsSatisfied();
		    
		   
	
	}	
	
	}

