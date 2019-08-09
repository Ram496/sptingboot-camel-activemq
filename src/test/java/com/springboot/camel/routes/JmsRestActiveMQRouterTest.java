
package com.springboot.camel.routes;

import static org.junit.Assert.assertFalse;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.reifier.RouteReifier;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import com.springboot.camel.AppRunner;

@RunWith(CamelSpringBootRunner.class)

@UseAdviceWith
@SpringBootApplication
@SpringBootTest(classes = AppRunner.class)

public class JmsRestActiveMQRouterTest {

	@Autowired
	private ProducerTemplate producerTemplate;

	@Autowired
	private ModelCamelContext context1;
	
	//String inputMessage = "GZXFRTJ675FTRHJJJ87zyxt000000000000017.450EURATZAT\n"
		//	+ ":20:TR234567,Zu87656z,Bhj876t\n" + ":32A:180123 Ship dual FERT chem\n" + ":36:12";


		
	 
	@Test
	public void messageTransfer() throws Exception {
		
		System.out.println("route name: "+context1.getRouteDefinitions().get(0));
		
		assertFalse(context1.getStatus().isStarted());



        RouteReifier.adviceWith(context1.getRouteDefinitions().get(0), context1, new AdviceWithRouteBuilder() {

            @Override

            public void configure() throws Exception {

//                    mockEndpoints();
            	
            	interceptSendToEndpoint("{{output.consumer}}")
            	.to("mock:process");
                 
            	interceptSendToEndpoint("{{output.queue}}")
            	.to("mock:outputqueue");
            	
            	interceptSendToEndpoint("{{error.queue}}")
            	.to("mock:error");
            	
            	//replaceFromWith("{{output.queue}}");

               // weaveAddLast().to("mock:out");


            }

        });



        // manual start camel

        context1.start();
        
        
        MockEndpoint mock = context1.getEndpoint("mock:process", MockEndpoint.class);
        MockEndpoint mockQueueEndoint = context1.getEndpoint("mock:outputqueue", MockEndpoint.class);
        MockEndpoint mockError = context1.getEndpoint("mock:error", MockEndpoint.class);
		
        String inputMessage = "GZXFRTJ675FTRHJJJ87zyxtGovind Real         U000000000000017.450EURATZAT\n"
				+ ":20:TR234567,Zu87656z,Bhj876t\n" + ":32A:180123 Ship dual FERT chem\n" + ":36:12";

        producerTemplate.sendBody("{{input.queue}}", inputMessage);

		//producerTemplate.withBody(inputMessage).to("{{input.queue}}");

       //List<Exchange> list = mock.getReceivedExchanges();
        
        
        mock.setResultWaitTime(1000);
		//mock.expectedBodiesReceived("Got it, Thanks");
		mock.expectedMessageCount(0);
		
		mockQueueEndoint.expectedMessageCount(0);
		//mockQueueEndoint.expectedBodiesReceived("Got it, Thanks");
		
		mockError.expectedMessageCount(1);
		
		mock.assertIsSatisfied();
		mockQueueEndoint.assertIsSatisfied();
		mockError.assertIsSatisfied();

	}
	
	/*
	@Test
	public void messageTransferFailureTest() throws Exception {
		
		
		assertFalse(context1.getStatus().isStarted());
			RouteReifier.adviceWith(context1.getRouteDefinitions().get(0), context1, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
            	
            	interceptSendToEndpoint("{{output.consumer}}")
            	.to("mock:process");
                 
            	interceptSendToEndpoint("{{output.queue}}")
            	.to("mock:outputqueue");
            	
            	interceptSendToEndpoint("{{error.queue}}")
            	.to("mock:error");

            }

        });


        // manual start camel

        context1.start();
        
        
        MockEndpoint mock = context1.getEndpoint("mock:process", MockEndpoint.class);
        MockEndpoint mockQueueEndoint = context1.getEndpoint("mock:outputqueue", MockEndpoint.class);
        MockEndpoint mockError = context1.getEndpoint("mock:error", MockEndpoint.class);
		//some invalid data
        
        producerTemplate.sendBody("{{input.queue}}", inputMessage);

        
        mock.setResultWaitTime(1000);
		//mock.expectedBodiesReceived("Got it, Thanks");
		mock.expectedMessageCount(0);
		
		mockQueueEndoint.expectedMessageCount(0);
		//mockQueueEndoint.expectedBodiesReceived("Got it, Thanks");
		
		mockError.expectedMessageCount(1);
		
		mock.assertIsSatisfied();
		mockQueueEndoint.assertIsSatisfied();
		mockError.assertIsSatisfied();

	}
	*/
	
	
	/*
	@Test
	public void transferFromJMStoRESTApi() throws Exception {

		context.start();
		producerTemplate.start();
		String inputMessage = "GZXFRTJ675FTRHJJJ87zyxtGovind Real         U000000000000017.450EURATZAT\n"
				+ ":20:TR234567,Zu87656z,Bhj876t\n" + ":32A:180123 Ship dual FERT chem\n" + ":36:12";

		producerTemplate.withBody(inputMessage).to("{{input.queue}}");

		// NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create();

		// NotifyBuilder notify = new NotifyBuilder(context)
		// .from("jms:inbound.queue")
		// .whenAnyDoneMatches(
		// body().isEqualTo(inputMessage)
		// ).create();

		Thread.sleep(2000);

		/*
		 * String response = consumerTemplate.receiveBody("{{output.consumer}}",
		 * String.class).setHeader(Exchange.HTTP_METHOD, simple("POST"))
		 * .setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		 */

		// System.out.println("response received from rest end point in test class:
		// "+response);

		/*
		 * ResponseEntity<String> response =
		 * restTemplate.getForEntity("{{output.consumer}}", String.class);
		 * 
		 * String s = response.getBody();
		 * System.out.println("response for restTemplate: "+s);
		 

		BrowsableEndpoint be = context.getEndpoint("{{output.queue}}", BrowsableEndpoint.class);
		List<Exchange> list = be.getExchanges();

		int messageCount = list.size() - 1;
		assertEquals(messageCount + 1, list.size());
		String body = list.get(messageCount).getIn().getBody(String.class);
		assertEquals("Got it, Thanks", body);

	}

	@Test
	public void messageTransferFromJMStoRESTApi() throws Exception {

		String inputMessage = "GZXFRTJ675FTRHJJJ87zyxtGovind Real         U000000000000017.450EURATZAT\n"
				+ ":20:TR234567,Zu87656z,Bhj876t\n" + ":32A:180123 Ship dual FERT \n" + ":36:12";

		// String hello = "testing with fake string";
		String response = "Got it, Thanks";
		
		
		MockEndpoint mockOut = context.getEndpoint("mock:end", MockEndpoint.class);
		String content = context.getTypeConverter().convertTo(String.class, mockOut);

		mockOut.expectedMessageCount(0);

		System.out.println("content is: " + content);

		mockOut.expectedBodiesReceived(response);
		
		producerTemplate.withBody(inputMessage).to("{{input.queue}}");


		Thread.sleep(2000);

		// mockOut.expectedBodiesReceived(response);

		// NotifyBuilder notify = new NotifyBuilder(context);
		// notify.from("jms:inbound.queue").whenBodiesReceived("Nothing found, All
		// ok").wereSentTo("jms:outbound.queue")
		// .create();
		

		MockEndpoint.assertIsSatisfied(context);

		// mockOut.assertIsSatisfied(20000);

		// assertMockEndpointsSatisfied();

	}
	
	*/


}
