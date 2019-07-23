package com.springboot.camel.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.springboot.camel.processor.JMSIncomingMessageProcessor;
import com.springboot.camel.processor.RestResponseProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JmsRestActiveMQRouter extends RouteBuilder {
	// private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Override
	public void configure() throws Exception {

		log.info("Configuring the Route to connect to ActiveMQ JMS and REST api");

		/*
		 * from("{{input.queue}}").log(LoggingLevel.DEBUG, log,
		 * "New message received").process(new MyProcessor())
		 * .setHeader(Exchange.HTTP_METHOD, simple("POST"))
		 * .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		 * .to("{{output.consumer}}") .log(LoggingLevel.DEBUG, log,
		 * "Message sent to the other queue").process(exchange -> { //String
		 * convertedMessage = " Got it, Thanks ";
		 * //exchange.getMessage().setBody(convertedMessage);
		 * }).to("{{output.queue}}").log(LoggingLevel.DEBUG, log,
		 * "Message has been received and response has been sent back to the jms sender"
		 * ) .end();
		 */

		from("{{input.queue}}").log("LoggingLevel.DEBUG, log,New message received from JMS ActiveMQ - \"{{input.queue}}\"")
			.process(new JMSIncomingMessageProcessor()).setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				
	    .to("{{output.consumer}}").log("LoggingLevel.DEBUG, log,Message has been delivered to REST Api - \"{{output.consumer}}\"")
			.process(new RestResponseProcessor())
		
	    .to("{{output.queue}}")
				.log("LoggingLevel.DEBUG, log, recevied response from REST Api call, response has been sent to the "
						+ "ActiveMQ out queue - \"{{output.queue}}\"")
		.end();

	}
}
