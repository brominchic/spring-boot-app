package org.example.spring.controller;

import org.example.spring.clients.UnauthorizedClient;
import org.example.spring.model.dto.OperationCreateDto;
import org.example.spring.model.entity.CurrencyEntity;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.CurrencyRepository;
import org.example.spring.repositories.OperationRepository;
import org.example.spring.service.component.OperationManualCacheComponent;
import org.example.spring.service.component.OperationSpringCacheComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private OperationManualCacheComponent operationManualCacheComponent;
    @Autowired
    private OperationSpringCacheComponent operationSpringCacheComponent;

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
    public void createRowsWithDataFromSlowService() {
        String uploadUrl = "http://localhost:" + port + "/example-application/unsecured/operation/create";
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 100; i++) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String json = "{" +
                    "\"sum\":1," +
                    "\"currency\":\"rub\"" +
                    "}";
            HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
            ResponseEntity<String> uploadResponse = restTemplate.postForEntity(uploadUrl, requestEntity, String.class);
        }
        List<OperationEntity> result = new ArrayList<>();
        operationRepository.findAll().forEach(result::add);
        assertEquals(100, result.size());
    }

    @Test
    public void inTimeWithCache() {
        Long firstTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            operationManualCacheComponent.createRowWithCache(1L, "rub");
        }
        Long secondTime = System.currentTimeMillis();
        List<OperationEntity> result = new ArrayList<>();
        operationRepository.findAll().forEach(result::add);
        assertEquals(100, result.size());
        assert (secondTime - firstTime < 10000);
    }

    @Test
    public void inTimeWithSpringCacheFromClient() {
        Long firstTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            operationSpringCacheComponent.createRowWithCacheFromClient(1L, "rub");
        }
        Long secondTime = System.currentTimeMillis();
        List<OperationEntity> result = new ArrayList<>();
        operationRepository.findAll().forEach(result::add);
        assertEquals(100, result.size());
        assert (secondTime - firstTime < 10000);
    }

    @Test
    public void notInTimeWithoutCache() {
        int cnt = 10;
        Long firstTime = System.currentTimeMillis();
        for (int i = 0; i < cnt; i++) {
            operationManualCacheComponent.createRowWithoutCache(1L, "rub");
        }
        Long secondTime = System.currentTimeMillis();
        List<OperationEntity> result = new ArrayList<>();
        operationRepository.findAll().forEach(result::add);
        assertEquals(cnt, result.size());
        assert (secondTime - firstTime > cnt * 100);
    }

    @Test
    void createRowsWithFeign() {

        for (int i = 0; i < 100; i++) {
            unauthorizedClient.createNewTransaction(OperationCreateDto.builder().currency("rub").sum(1L).build());
        }

        assertEquals(100, operationRepository.count());
    }
}
