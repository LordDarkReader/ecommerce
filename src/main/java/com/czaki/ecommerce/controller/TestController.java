package com.czaki.ecommerce.controller;

import com.czaki.ecommerce.service.IntegrationTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private IntegrationTestService integrationTestService;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping("/test-info-service")
    public String testIntegrationInfoService() {
        return integrationTestService.testIntegrationInfoService();
    }
}
