package com.northwind.orderservice.adapters;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;

@Component
public class ShippingServiceClientImpl implements ShippingServiceClient {

    private RestTemplate restTemplate;
    private ShippingServiceClientConfig config;

    private EurekaClient eurekaClient;

    public ShippingServiceClientImpl(ShippingServiceClientConfig config, RestTemplateBuilder restTemplateBuilder, EurekaClient eurekaClient) {
        this.config = config;
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
        this.eurekaClient = eurekaClient;
    }

    @Override
    @Retryable(maxAttempts = 3,
               backoff = @Backoff(delay = 100, maxDelay = 2000, random = true))
    @HystrixCommand(groupKey = "shipping-service",
                    commandKey = "getFreightAmount",
                    fallbackMethod = "getFreightAmountFallback")
    public BigDecimal getFreightAmount(String country) {

        InstanceInfo nextServerFromEureka = eurekaClient.getNextServerFromEureka("SHIPPINGSERVICE", false);
        String homePageUrl = nextServerFromEureka.getHomePageUrl();
        String url = homePageUrl+"shipping/rates?country="+country;

        ResponseEntity<BigDecimal> model = restTemplate.getForEntity(
                url,
                    BigDecimal.class);

        return model.getBody();
    }

    public BigDecimal getFreightAmountFallback(String country) {
        return new BigDecimal("26.98");
    }
}
