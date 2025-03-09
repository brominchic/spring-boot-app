package org.example.spring.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

@FeignClient(name = "CurrencyClient", url = "http://localhost:9999/example-application")
public interface CurrencyClient {

    @GetMapping(value = "unsecured/slow/test")
    HashMap<String, String> getAll();


}
