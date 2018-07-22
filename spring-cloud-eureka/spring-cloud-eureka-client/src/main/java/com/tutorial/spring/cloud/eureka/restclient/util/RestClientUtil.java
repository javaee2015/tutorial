/*
 * RestClientUtil.java
 *
 * Copyright (c) 2016 CapitaLand, Inc.
 * An Unpublished Work.  All Rights Reserved.
 * 
 */
package com.tutorial.spring.cloud.eureka.restclient.util;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientUtil {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(RestClientUtil.class);

	@Autowired
	private RestTemplate restTemplate;

	public String doGet(String url, Map<String, String> headerParam, Map<String, Object> uriParams) {
		return (String) doGet(url, headerParam, uriParams, String.class);
	}

	public Object doGet(String url, Map<String, String> headerParam, Map<String, Object> uriParams,
			Class<?> responseType) {

		HttpHeaders headers = new HttpHeaders();
		if (headerParam != null && !headerParam.isEmpty()) {
			Set<String> keySet = headerParam.keySet();
			for (String key : keySet) {
				headers.add(key, headerParam.get(key));
			}
		}

		HttpEntity<?> response = null;

		try {
			if (uriParams != null) {
				response = restTemplate.exchange(url, HttpMethod.GET, null, responseType, uriParams);

			} else {
				response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
			}

		} catch (HttpClientErrorException e) {
			log.error(e.getClass().getSimpleName() + " occurs when trying to send get request.", e);
			throw e;
		}

		Object responseBody = response.getBody();

		return responseBody;
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		return restTemplate;
	}
}
