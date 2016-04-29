package com.jinpalhawang.jambudvipa;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
@RefreshScope
public class AccountService {

  private static final Logger log = LoggerFactory.getLogger(AccountService.class);

  @Value("${responsePrefix}")
  private String responsePrefix;

  @Value("${fallbackResponse}")
  private String fallbackResponse;

  @HystrixCommand(fallbackMethod = "fallback")
  public String helloAccount() {
    RestTemplate restTemplate = new RestTemplate();
    URI uri = URI.create("http://localhost:8091"); // TODO: Replace with eureka client call

    String response = responsePrefix + ": " + restTemplate.getForObject(uri, String.class);
    log.info(response);

    return response;
  }

  public String fallback() {
    String response = fallbackResponse;
    log.info(response);

    return response;
  }
}
