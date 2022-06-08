package com.czaki.ecommerce.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IntegrationTestServiceImpl implements IntegrationTestService{




    @Override
    public String testIntegrationInfoService() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://info-service:8081/test", String.class);
        return forEntity.getBody();
    }

}
