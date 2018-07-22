package com.tutorial.spring.cloud.eureka.client.error;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Ticker symbol")
public class InvalidTickerException extends RuntimeException {

	public InvalidTickerException(String tickerSymbol) {
		super(MessageFormat.format("No such Ticker symbol {0}", tickerSymbol));
	}

}
