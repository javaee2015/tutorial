package com.tutorial.spring.cloud.eureka.client;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.discovery.EurekaClient;
import com.tutorial.spring.cloud.eureka.client.dto.Price;
import com.tutorial.spring.cloud.eureka.client.error.InvalidTickerException;
import com.tutorial.spring.cloud.eureka.restclient.util.RestClientUtil;

@RestController
@RequestMapping("/api/v2")
public class TickerSymbolController {
	@Autowired
	private EurekaClient eurekaClient;

	@Autowired
	private RestClientUtil restClientUtil;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${quandl.apiUrl}")
	private String quandlApiUrl;

	@RequestMapping(method = RequestMethod.GET, value = "/ping")
	public String ping() {
		return MessageFormat.format("Application name: {0}", eurekaClient.getApplication(appName));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{tickersymbol}/closePrice", produces = MediaType.APPLICATION_JSON_VALUE)
	public String closePrice(@PathVariable("tickersymbol") String tickerSymbol, @RequestParam String startDate,
			@RequestParam String endDate) {
		String quandlUrlWithDatasetCode = MessageFormat.format(quandlApiUrl, tickerSymbol);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(quandlUrlWithDatasetCode)
				.queryParam("column_index", 4)
				.queryParam("start_date", startDate)
				.queryParam("end_date", endDate);
		
		try {
			String json = restClientUtil.doGet(builder.toUriString(), null, null);
			return json;
		} catch (HttpClientErrorException e) {
			throw new InvalidTickerException(tickerSymbol);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{tickersymbol}/200dma")
	public Price get200dma(@PathVariable("tickersymbol") String tickerSymbol, @RequestParam String startDate,
			@RequestParam String endDate) {
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/200dma")
	public Price get200dmaFor1000TickerSymbols(@RequestBody String tickerSymbol, @RequestParam String startDate) {
		return null;
	}
}
