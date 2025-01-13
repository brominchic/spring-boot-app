package org.example.spring.controller;

import feign.FeignException;
import org.example.spring.clients.AuthorizedClient;
import org.example.spring.clients.UnauthorizedClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatusCode;

@SpringBootTest
@EnableFeignClients
public class ControllerAuthFeignTest {
    @Autowired
    private AuthorizedClient authorizedClient;
    @Autowired
    private UnauthorizedClient unauthorizedClient;

    @Test
    public void testUnauthorizedAccessToUnsecuredEndpoint() {
        Assertions.assertEquals("unsecured", unauthorizedClient.unsecuredTest());
    }

    @Test
    public void testUnauthorizedAccessToSecuredEndpoint() {
        try {
            unauthorizedClient.securedTest();
        } catch (FeignException e) {
            Assertions.assertEquals(401, e.status());
        }

    }

    @Test
    public void testAuthorizedAccessToSecuredEndpoint() {
        Assertions.assertEquals("unsecured", authorizedClient.unsecuredTest());
    }

    @Test
    public void testAuthorizedAccessToUnsecuredEndpoint() {
        Assertions.assertEquals("secured", authorizedClient.securedTest());
    }
}
