package org.example.spring.clients;

import org.example.spring.model.dto.OperationCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "UnsecuredClient", url = "http://localhost:9999/example-application")
public interface UnauthorizedClient {
    @GetMapping(value = "/secured/test")
    String securedTest();

    @GetMapping(value = "/unsecured/test")
    String unsecuredTest();

    @PostMapping("/unsecured/operation/create")
    void createNewTransaction(@RequestBody OperationCreateDto operationCreateDto);
}
