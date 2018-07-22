package com.tutorial.spring.cloud.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.tutorial.spring.cloud.eureka.client.TickerSymbolController;

@FeignClient("spring-cloud-eureka-client")
public interface GreetingClient extends TickerSymbolController {
}
