package org.example.spring.service.component;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.example.spring.clients.CurrencyClient;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.OperationRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class OperationManualCacheComponent {
    private final CurrencyClient client;
    private final OperationRepository repository;
    private final Cache<String, String> currencies = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .maximumSize(100)
            .build();

    public void createRowWithCache(Long sum, String currency) {
        if (currencies.asMap().isEmpty()) {
            fillTheMap();
        }
        String code = currencies.getIfPresent(currency);
        repository.save(OperationEntity.
                builder().
                sum(sum).
                currencyCode(code)
                .build());
    }

    public void createRowWithoutCache(Long sum, String currency) {
        String code = client.getAll().get(currency);
        repository.save(OperationEntity.
                builder().
                sum(sum).
                currencyCode(code)
                .build());
    }

    private void fillTheMap() {
        currencies.cleanUp();
        HashMap<String, String> data = client.getAll();
        for (HashMap.Entry<String, String> str : data.entrySet()) {
            currencies.put(str.getKey(), str.getValue());
        }
    }
}
