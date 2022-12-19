package com.lutrapp.microservices.camelmicroservicesb.router;

import java.math.BigDecimal;

import com.lutrapp.microservices.camelmicroservicesb.CurrencyExchange;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveMqReceiverRouter extends RouteBuilder{

	@Autowired
	private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
	
	@Autowired
	private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;
	
	@Override
	public void configure() throws Exception {
		
//		from("activemq:my-activemq-queue")
//		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
//		.bean(myCurrencyExchangeTransformer)
//		.bean(myCurrencyExchangeProcessor)
//		.to("log: received-message-from-active-mq");
	
		from("activemq:my-activemq-xml-queue")
		.unmarshal().jacksonXml(CurrencyExchange.class)
		.to("log: received-message-from-active-mq");
		
		from("activemq:split-queue")
		.to("log: received-message-from-active-mq");
	}
	
	
}

	@Component
	class MyCurrencyExchangeProcessor {
		Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

		public void processMessage(CurrencyExchange currencyExchange) {
			logger.info("Do some processing with currencyExchange.getConversionMultiple() value which is {}", currencyExchange.getConversionMultiple());
			
		}
	}
	
	@Component
	class MyCurrencyExchangeTransformer {
		Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeTransformer.class);

		public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {
			currencyExchange.setConversionMultiple(currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));
			
			return currencyExchange;
		}
	}



