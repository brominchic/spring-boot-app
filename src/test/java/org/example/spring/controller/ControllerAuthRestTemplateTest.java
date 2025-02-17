package org.example.spring.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class ControllerAuthRestTemplateTest {
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void setup() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testUnauthorizedAccessToUnsecuredEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:9999/example-application/unsecured/test", String.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals("unsecured", response.getBody());
    }

    @Test
    public void testAuthorizedAccessToUnsecuredEndpoint() {
        String url = "http://localhost:9999/example-application/unsecured/test";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("brom", "password");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals("unsecured", response.getBody());
    }

    @Test
    public void testUnauthorizedAccessToSecuredEndpoint() {
        try {
            restTemplate.getForEntity("http://localhost:9999/example-application/secured/test", String.class);
        } catch (HttpClientErrorException e) {
            Assertions.assertEquals(HttpStatusCode.valueOf(401), e.getStatusCode());
        }
    }

    @Test
    public void testAuthorizedAccessToSecuredEndpoint() {
        String url = "http://localhost:9999/example-application/secured/test";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("brom", "password");
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertEquals("secured", response.getBody());
    }
}
