package com.springboot.camel.routes;

import org.apache.camel.CamelContext;

import org.apache.camel.EndpointInject;

import org.apache.camel.Produce;

import org.apache.camel.ProducerTemplate;

import org.apache.camel.component.mock.MockEndpoint;

import org.apache.camel.spring.SpringRouteBuilder;

import org.apache.camel.test.spring.CamelSpringBootRunner;

import org.apache.camel.test.spring.MockEndpointsAndSkip;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Configuration;

import org.springframework.test.annotation.DirtiesContext;

@RunWith(CamelSpringBootRunner.class)

@MockEndpointsAndSkip("direct:b")

@SpringBootApplication

@SpringBootTest(classes = MockEndpointsSkipsDirtiesTest.class)

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class MockEndpointsSkipsDirtiesTest {

	@Produce("direct:a")
	private ProducerTemplate producer;

	@EndpointInject("mock:end")
	private MockEndpoint end;

	@EndpointInject("mock:direct:b")
	private MockEndpoint directB;

	@Autowired
	private CamelContext context;

	@Configuration
	public static class Config extends SpringRouteBuilder {

		@Override

		public void configure() throws Exception {

			from("direct:a").to("direct:b");

			from("direct:b").to("mock:end");

		}

	}

	@Test

	public void testMock() throws InterruptedException {

		end.expectedMessageCount(0);

		directB.expectedBodiesReceived("hello");

		producer.sendBody("hello");

		MockEndpoint.assertIsSatisfied(context);

	}

	@Test

	public void testMock2() throws InterruptedException {

		end.expectedMessageCount(0);

		directB.expectedBodiesReceived("bye");

		producer.sendBody("bye");

		MockEndpoint.assertIsSatisfied(context);

	}

}