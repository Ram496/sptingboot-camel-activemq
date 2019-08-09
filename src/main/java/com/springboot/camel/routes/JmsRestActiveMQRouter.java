package com.springboot.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.camel.model.TransactionMessage;
import com.springboot.camel.processor.RestResponseProcessor;
import com.springboot.camel.validation.InputMessageValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JmsRestActiveMQRouter extends RouteBuilder {

	JacksonDataFormat jsonDataFormat = new JacksonDataFormat(TransactionMessage.class);

	@Autowired
	InputMessageValidator messageValidator;

	@Override
	public void configure() throws Exception {

		log.info("Configuring the Route builder");

		// error handling, if in case of non recoverable exceptions
		errorHandler(defaultErrorHandler().maximumRedeliveries(2).redeliveryDelay(1000));
		// handling exception, if there is any exception occurred message will be sent
		// to error queue
		onException(Exception.class).maximumRedeliveries(0)
				.log("exception has caught, message has been sent to error queue").handled(true).to("{{error.queue}}");

		// receiving message from input queue and populating the bean values and
		// validating the bean
		from("{{input.queue}}").log("New message received from: \"{{input.queue}}\"")
				.bean(messageValidator, "saveData")
				.bean(messageValidator, "validateData")
				.bean(messageValidator, "validateTransaction")
					.marshal(jsonDataFormat)
					.setHeader(Exchange.HTTP_METHOD, simple("POST"))
					.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
					
				.to("{{output.consumer}}")
								.log("Message has been delivered to REST Api - \"{{output.consumer}}\"")

				.process(new RestResponseProcessor()).

				to("{{output.queue}}").log("Message has been sent to: \"{{output.queue}}\"");

	}
}
