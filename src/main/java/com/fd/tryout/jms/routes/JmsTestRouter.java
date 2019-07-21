package com.fd.tryout.jms.routes;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JmsTestRouter extends RouteBuilder {
	// private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	@Override
	public void configure() throws Exception {

		log.info("Configuring route");

		from("{{input.queue}}").log(LoggingLevel.DEBUG, log, "New message received").process(new MyProcessor())
				.setHeader(Exchange.HTTP_METHOD, simple("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json")).
					to("{{output.consumer}}")
						.log(LoggingLevel.DEBUG, log, "Message sent to the other queue")
							.process(exchange -> {
									String convertedMessage = " Got it, Thanks ";
									exchange.getMessage().setBody(convertedMessage);
							})
							.to("{{output.queue}}")
							.log(LoggingLevel.DEBUG, log,"Message has been received and response has been sent back to the jms sender")
				.end();

	}
}
