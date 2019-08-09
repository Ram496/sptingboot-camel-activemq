package com.springboot.camel.routes;

import org.apache.camel.builder.RouteBuilder;


public class TestRoutes extends RouteBuilder {


	    @Override

	    public void configure() throws Exception {

	        from("jms:inbound")

	            .to("jms:outbound");

	    }

	}

