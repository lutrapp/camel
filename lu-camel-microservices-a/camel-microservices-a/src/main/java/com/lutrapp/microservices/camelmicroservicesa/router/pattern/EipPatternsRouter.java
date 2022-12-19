package com.lutrapp.microservices.camelmicroservicesa.router.pattern;

import java.util.ArrayList;
import java.util.List;

import com.lutrapp.microservices.camelmicroservicesa.CurrencyExchange;
import com.lutrapp.microservices.camelmicroservicesa.router.pattern.EipPatternsRouter.ArrayListAggregationStrategy;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EipPatternsRouter  extends RouteBuilder{

	public class ArrayListAggregationStrategy implements AggregationStrategy {
		//1,2,3
		//null, 1 => [1]
		//[1], 2 => [1,2]
		// [1,2], 3 =>  [1,2,3]
		
		@Override
		public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	        Object newBody = newExchange.getIn().getBody();
	        ArrayList<Object> list = null;
	        if (oldExchange == null) {
	            list = new ArrayList<Object>();
	            list.add(newBody);
	            newExchange.getIn().setBody(list);
	            return newExchange;
	        } else {
	            list = oldExchange.getIn().getBody(ArrayList.class);
	            list.add(newBody);
	            return oldExchange;
	        }
	    }

	}

	@Autowired
	SplitterComponent splitter;
	
	@Override
	public void configure() throws Exception {
		getContext().setTracing(true);
		
		errorHandler(deadLetterChannel("activemq:dead-letter-queue"));
		//Pipeline
		//Content based routing - choice()
		//Multicast
		
//		from("timer:multicast?period=10000")
//		.multicast()
//		.to("log:something1", "log:something2", "log:something3");
		
//		from("file:files/csv")
//		.unmarshal().csv()
//		.split(body())
////		.to("log:split-files");
//		.to("activemq:split-queue");
		
		//Message1, Message2, Message3
//		from("file:files/csv")
//		.convertBodyTo(String.class)
////		.split(body(), ",")
//		.split(method(splitter))
//		.to("activemq:split-queue");
		
		//Aggregate
		//Messages => Aggregate => Endpoint
		//to, 3
		from("file:files/aggregate-json")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		.aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
		.completionSize(3)
//		.completionTimeout(HIGHEST)
		.to("log:aggregate-json");
		
		String routingSlip = "direct:endpoint1,direct:endpoint2";
		
		
		
		from("timer:routingSlip?period={{timePeriod}}")
		.wireTap("log:wire-tap")
		.transform().constant("message is hardcoded!!")
		.routingSlip(simple(routingSlip));
		
		from("direct:endpoint1")
		.to("{{endpoint-for-logging}}"); //at application.properties
		
		from("direct:endpoint2")
		.to("log:directenpoint2");
		
		from("direct:endpoint3")
		.to("log:directenpoint3");
	}

}

@Component
class SplitterComponent{
	public List<String> splitInput(String body){
		return List.of("ABC", "DEF", "GHI");
	}
}