package org.example.spring.service.component;

import lombok.RequiredArgsConstructor;
import org.example.spring.clients.CurrencyClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyCachedComponent {
    private final CurrencyClient client;

    @Cacheable(cacheManager = "caffeineManager", cacheNames = "codes")
    String getCurrencyCode(String currency) {
        return client.getAll().get(currency);
    }
}
