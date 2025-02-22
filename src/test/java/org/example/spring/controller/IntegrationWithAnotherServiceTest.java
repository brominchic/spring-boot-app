package org.example.spring.controller;

import org.example.spring.model.entity.CurrencyEntity;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.CurrencyRepository;
import org.example.spring.repositories.OperationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class IntegrationWithAnotherServiceTest extends SpringBootApplicationTest {
    @LocalServerPort
    private final Integer port = 9999;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    OperationRepository operationRepository;

    @Test
    @Transactional
    public void createRowsWithDataFromSlowService() throws InterruptedException {
        currencyRepository.save(CurrencyEntity.
                builder().
                id(1L).
                name("rub").
                code("1l").
                build());
        currencyRepository.save(CurrencyEntity.
                builder().
                id(2L).
                name("euro").
                code("2l").
                build());
        String uploadUrl = "http://localhost:" + port + "/example-application/unsecured/operation/create";
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 100; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("sum", 1);
            body.add("currency", "rub");
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> uploadResponse = restTemplate.postForEntity(uploadUrl, requestEntity, String.class);
        }
        List<OperationEntity> result = new ArrayList<>();
        operationRepository.findAll().forEach(result::add);
        Assertions.assertEquals(100, result.size());
    }
}
