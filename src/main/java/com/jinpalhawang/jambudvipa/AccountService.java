package com.jinpalhawang.jambudvipa;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class AccountService {

  private static final Logger log = LoggerFactory.getLogger(AccountService.class);

  @HystrixCommand(fallbackMethod = "fallback")
  public String helloAccount() {
    RestTemplate restTemplate = new RestTemplate();
    URI uri = URI.create("http://localhost:8091");

    String response = "From Middle: " + restTemplate.getForObject(uri, String.class);
    log.info(response);

    return response;
  }

  public String fallback() {
    String response = "Fallback: Hello World!";
    log.info(response);

    return response;
  }
}
