package com.lutrapp.microservices.camelmicroservicesa.routes.a;

import java.time.LocalDateTime;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
//import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyFirstTimerRouter extends RouteBuilder {
	


	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent simpleLoggingProcessingComponent;

	@Override
	public void configure() throws Exception {
		//queue/ timer
		//transformation
		//database / log
//		Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		
		from("timer:first-timer") //null
		.log("${body}")
		.transform().constant("My constant message")
		.log("${body}")
//		.transform().constant("Local time is: " + LocalDateTime.now().getMinute() )
//		.bean("getCurrentTimeBean")
		.bean(getCurrentTimeBean)
		.log("${body}")
//		.bean(simpleLoggingProcessingComponent)
		.process(new SimpleLoggingProcessor())
		.log("${body}")
		.to("log:first-timer"); //database
		
	}

}

@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Time now is" + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProcessingComponent {
	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

	public void process(String message) {
		 logger.info("SimpleLoggingProcessingComponent {}", message);
	}
}

 class SimpleLoggingProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
		
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());

	}

}
