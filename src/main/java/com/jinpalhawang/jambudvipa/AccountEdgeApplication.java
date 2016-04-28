package com.jinpalhawang.jambudvipa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableCircuitBreaker
public class AccountEdgeApplication {

  @Autowired
  private AccountService accountService;

  private static final Logger log = LoggerFactory.getLogger(AccountEdgeApplication.class);

  @RequestMapping("/")
  public String properties() {
    String response = accountService.helloAccount();
    log.info(response);
    return response;
  }

  public static void main(String[] args) {
    SpringApplication.run(AccountEdgeApplication.class, args);
  }

}
