package com.lutrapp.microservices.camelmicroservicesa.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class ActiveMqSenderRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		// timer
//		from("timer:active-mq-timer?period=10000")
//		.transform().constant("My message for active mq")
//		.log("${body}")
//		.to("activemq:my-activemq-queue");
		//queue
		
		//json
//		from("file:files/json")
//		.log("${body}")
//		.to("activemq:my-activemq-queue");
		
		from("file:files/xml")
		.log("${body}")
		.to("activemq:my-activemq-xml-queue");
	}

}
