package com.jinpalhawang.jambudvipa;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class AccountEdgeApplication {

  private static final Logger log = LoggerFactory.getLogger(AccountEdgeApplication.class);

  @Autowired
  void setEnvironment(Environment e) {
    log.info("Environment Property: server.port=" + e.getProperty("server.port"));
    log.info("Environment Property: responsePrefix=" + e.getProperty("responsePrefix"));
    log.info("Environment Property: fallbackResponse=" + e.getProperty("fallbackResponse"));
  }

  public static void main(String[] args) {
    SpringApplication.run(AccountEdgeApplication.class, args);
  }

}

@RestController
class AccountEdgeRestController {

  private static final Logger log = LoggerFactory.getLogger(AccountEdgeRestController.class);

  @Autowired
  private DiscoveryClient discoveryClient;

  @Autowired
  private AccountService accountService;

  @RequestMapping("/")
  public String properties() {
    String response = accountService.helloAccount();
    log.info(response);
    return response;
  }

  @RequestMapping("/service-instances/{applicationName}")
  public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
    return this.discoveryClient.getInstances(applicationName);
  }

}
