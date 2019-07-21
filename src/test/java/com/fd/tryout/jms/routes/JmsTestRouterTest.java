package com.fd.tryout.jms.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@MockEndpoints
@UseAdviceWith
public class JmsTestRouterTest {

	@Autowired
	private CamelContext camelContext;
	@Autowired
	private ProducerTemplate producerTemplate;

	@Test
	public void test() throws Exception {

		camelContext.getRouteDefinition("JmsTestRouter")

				.adviceWith(camelContext, new AdviceWithRouteBuilder() {

					@Override

					public void configure() throws Exception {

						replaceFromWith("direct:in");

						// send the outgoing message to mock:out

						weaveAddLast().to("mock:out");

					}

				});

		camelContext.start();

		MockEndpoint mockOut = camelContext.getEndpoint("mock:out", MockEndpoint.class);

		mockOut.expectedMessageCount(1);

		producerTemplate.sendBody("direct:in", "Testing message");

		mockOut.assertIsSatisfied();

	}

}