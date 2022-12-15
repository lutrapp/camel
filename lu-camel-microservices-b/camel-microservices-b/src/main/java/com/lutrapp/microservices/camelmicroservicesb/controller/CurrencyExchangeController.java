package com.lutrapp.microservices.camelmicroservicesb.controller;

import java.math.BigDecimal;

import com.lutrapp.microservices.camelmicroservicesb.CurrencyExchange;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange findCoversionValue(
			@PathVariable String from,
			@PathVariable String to
			) {
//		return new CurrencyExchange(10001L, "USD", "INR", BigDecimal.TEN);
		return new CurrencyExchange(10001L, from, to, BigDecimal.TEN);
	}
	
}
