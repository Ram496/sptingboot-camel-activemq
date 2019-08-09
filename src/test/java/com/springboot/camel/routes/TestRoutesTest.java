package com.springboot.camel.routes;

import static org.junit.Assert.assertFalse;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.mock.MockValueBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.reifier.RouteReifier;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(CamelSpringBootRunner.class)

@UseAdviceWith

@SpringBootApplication

@SpringBootTest(classes = TestRoutesTest.class)

public class TestRoutesTest {

	@Autowired

	ProducerTemplate producerTemplate;

	@Autowired

	ModelCamelContext camelContext;

	@Test

    public void shouldMockEndpoints() throws Exception {

        // context should not be started because we enabled @UseAdviceWith
/*
        assertFalse(camelContext.getStatus().isStarted());



       // List<String> list = camelContext.ge
        
        System.out.println("name of the route..."+camelContext.getRouteDefinitions().get(1));
        
		
		  RouteReifier.adviceWith(camelContext.getRouteDefinitions().get(1),
		  camelContext, new AdviceWithRouteBuilder() {
		  
		  @Override
		  
		  public void configure() throws Exception {
		  
				
						/*
						 * interceptSendToEndpoint("{{output.queue}}") .to("mock:process");
						 */  
		//				  replaceFromWith("jms:outbound");
			//			  
//						  weaveAddLast().to("mock:process");
						 		  
		  //}
		  
		  //});
		 


        // manual start camel

        camelContext.start();



        MockEndpoint mock = camelContext.getEndpoint("mock:process", MockEndpoint.class);



        // Given

        String msg = "msg";

        String s1 = "no";
       
        MockValueBuilder s = mock.allMessages().body();
        
        System.out.println("s::"+s);

        // When

        producerTemplate.sendBody("jms:inbound", msg);



        // Then


        //Thread.sleep(2000);
        mock.expectedMessageCount(1);
        mock.expectedBodiesReceived(msg);
        

        mock.assertIsSatisfied();

    }



}