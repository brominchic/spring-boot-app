package org.example.spring.clients;


import org.example.spring.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "SecuredClient", url = "http://localhost:9999/example-application", configuration = FeignConfig.class)
public interface AuthorizedClient {
    @GetMapping(value = "/secured/test")
    String securedTest();

    @GetMapping(value = "/unsecured/test")
    String unsecuredTest();
}
