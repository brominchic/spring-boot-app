package org.example.spring.controller;

import org.example.spring.clients.UnauthorizedClient;
import org.example.spring.model.dto.OperationCreateDto;
import org.example.spring.model.entity.CurrencyEntity;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.CurrencyRepository;
import org.example.spring.repositories.OperationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationWithAnotherServiceTest extends SpringBootApplicationTest {
    @LocalServerPort
    private final Integer port = 9999;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    private UnauthorizedClient unauthorizedClient;

    @BeforeEach
    void setUp() {

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
    }

    @AfterEach
    void tearDown() {
        operationRepository.deleteAll();
        currencyRepository.deleteAll();
    }

    @Test
    public void createRowsWithDataFromSlowService() throws InterruptedException {
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
        assertEquals(100, result.size());
    }

    @Test
    void createRowsWithFeign() {

        for (int i = 0; i < 100; i++) {
            unauthorizedClient.createNewTransaction(OperationCreateDto.builder().currency("rub").sum(1L).build());
        }

        assertEquals(100, operationRepository.count());
    }
}
