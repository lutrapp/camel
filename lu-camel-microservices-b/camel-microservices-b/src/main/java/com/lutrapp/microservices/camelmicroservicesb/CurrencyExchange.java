package com.lutrapp.microservices.camelmicroservicesb;

import java.math.BigDecimal;

//JSON
//CurrencyExchange //from log
//	{"id": 1000,
//	  "from": "USD",
//	  "to": "INR",
//	  "conversionMultiple": 70}

public class CurrencyExchange {
	private Long id;
	private String from;
	private String to;
	private BigDecimal conversionMultiple;
	
	public CurrencyExchange() {}
	
	public CurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	@Override
	public String toString() {
		return "CurrencyExchange [id=" + id + ", from=" + from + ", to=" + to + ", conversionMultiple="
				+ conversionMultiple + "]";
	}
	
}
