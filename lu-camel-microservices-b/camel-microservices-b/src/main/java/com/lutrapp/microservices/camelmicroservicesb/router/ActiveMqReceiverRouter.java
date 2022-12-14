package com.lutrapp.microservices.camelmicroservicesb.router;

import com.lutrapp.microservices.camelmicroservicesb.CurrencyExchange;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		//JSON
		//CurrencyExchange //from log
//			{"id": 1000,
//			  "from": "USD",
//			  "to": "INR",
//			  "conversionMultiple": 70}
		
		from("activemq:my-activemq-queue")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		.log("${body}")
		.to("log: received-message-from-active-mq");
	}

}


