package com.jinpalhawang.jambudvipa;

import java.net.URI;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
@RefreshScope
public class AccountService {

  @Value("${fallback.account.firstname}")
  private String fallbackAccountFirstName;

  @Value("${fallback.account.lastname}")
  private String fallbackAccountLastName;

  @Autowired
  RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "fallback")
  public Account getAccount() {
    final URI uri = URI.create("http://account-middle/accounts/search/findByFirstName?firstName=Jinpa");
    final Account account = this.restTemplate.getForObject(uri, Account.class);

    return account;
  }

  public Account fallback() {
    return new Account(fallbackAccountFirstName, fallbackAccountLastName, new HashMap<String, String>());
  }

}
