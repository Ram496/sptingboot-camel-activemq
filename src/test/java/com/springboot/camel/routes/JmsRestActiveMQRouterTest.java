
package com.springboot.camel.routes;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.spi.BrowsableEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class JmsRestActiveMQRouterTest {

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext context;



	@Test
	public void transferFromJMStoRESTApi() throws Exception {
		String inputMessage = "GZXFRTJ675FTRHJJJ87zyxtGovind Real         U000000000000017.450EURATZAT\n"
				+ ":20:TR234567,Zu87656z,Bhj876t\n" + ":32A:180123 Ship dual FERT chem\n" + ":36:12";
	
		template.sendBody("jms:inbound.queue", inputMessage);
		
		//NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create();

		//NotifyBuilder notify = new NotifyBuilder(context)
			//	  .from("jms:inbound.queue")
			//	  .whenAnyDoneMatches(
			//	    body().isEqualTo(inputMessage)
			//	  ).create();
		
		Thread.sleep(2000);
		  BrowsableEndpoint be = context.getEndpoint("jms:outbound.queue",BrowsableEndpoint.class); 
		  List<Exchange> list = be.getExchanges();
		  assertEquals(1, list.size()); 
		  String body = list.get(0).getIn().getBody(String.class);
		  assertEquals("Got it, Thanks", body);
		 
	}
	
	
	
	/*	@Test
	public void messageTransferFromJMStoRESTApi() throws Exception {

		String inputMessage = "GZXFRTJ675FTRHJJJ87zyxtGovind Real         U000000000000017.450EURATZAT\n"
				+ ":20:TR234567,Zu87656z,Bhj876t\n" + ":32A:180123 Ship dual FERT chem\n" + ":36:12";

		// String hello = "testing with fake string";
		String response = "Nothing found, All ok";

		MockEndpoint mockOut = context.getEndpoint("jms:outbound.queue", MockEndpoint.class);

		template.sendBody("jms:inbound.queue", inputMessage);

		Thread.sleep(2000);

		mockOut.expectedMessageCount(1);
		// mockOut.expectedBodiesReceived(response);

		NotifyBuilder notify = new NotifyBuilder(context);
		notify.from("jms:inbound.queue").whenBodiesReceived("Nothing found, All ok").wereSentTo("jms:outbound.queue")
				.create();

		String content = context.getTypeConverter().convertTo(String.class, mockOut);

		System.out.println("content is: " + content);

		mockOut.expectedBodiesReceived(response);

		// MockEndpoint.assertIsSatisfied(context);

		mockOut.assertIsSatisfied(20000);

		// assertMockEndpointsSatisfied();
	}*/

}
