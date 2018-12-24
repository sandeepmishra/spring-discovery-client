package org.springboot.starter.springdiscoveryclient.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/discovery-client")
public class DiscoveryCientController {


    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @RequestMapping("/instance")
    public String callService(){
       RestTemplate restTemplate = restTemplateBuilder.build();
       InstanceInfo instanceinfo  = eurekaClient.getNextServerFromEureka("spring-discovery-service", false);
       String baseURL  = instanceinfo.getHomePageUrl();
       System.out.println(baseURL+"/discovery-service/instance");
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseURL+"/discovery-service/instance", HttpMethod.GET, null, String.class);
        return responseEntity.getBody();
    }

}
