package com.jinpalhawang.jambudvipa;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@RibbonClient(name = "account-middle", configuration = AccountMiddleConfiguration.class)
public class AccountEdgeRestController {

  private static final Logger log = LoggerFactory.getLogger(AccountEdgeRestController.class);

  @Autowired
  private DiscoveryClient discoveryClient;

  @Autowired
  private AccountService accountService;

  @LoadBalanced
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @RequestMapping("/")
  public Account getAccount() {
    final Account response = accountService.getAccount();
    log.info(response.toString());
    return response;
  }

  @RequestMapping("/service-instances/{applicationName}")
  public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
    return this.discoveryClient.getInstances(applicationName);
  }

}
