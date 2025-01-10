package org.example.spring.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "UnsecuredClient", url = "http://localhost:9999/example-application")
public interface UnauthorizedClient {
    @GetMapping(value = "/secured/test")
    String securedTest();

    @GetMapping(value = "/unsecured/test")
    String unsecuredTest();
}
