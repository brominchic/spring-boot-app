package org.example.spring.clients;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

@FeignClient(name = "CurrencyCachedClient", url = "http://localhost:9999/example-application")
public interface CurrencyCachedClient {

    @Cacheable(cacheManager = "caffeineManager", cacheNames = "codesMap")
    @GetMapping(value = "unsecured/slow/test")
    HashMap<String, String> getAll();

}
