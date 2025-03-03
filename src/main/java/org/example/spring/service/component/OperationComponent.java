package org.example.spring.service.component;

import lombok.RequiredArgsConstructor;
import org.example.spring.clients.CurrencyClient;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.OperationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class OperationComponent {
    private final CurrencyClient client;
    private final OperationRepository repository;
    private final HashMap<String, String> currencies;

    public void createRowWithCache(Long sum, String currency) {
        if (currencies.isEmpty()) {
            fillTheMap();
        }
        String code = currencies.get(currency);
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

    @Cacheable
    private void fillTheMap() {
        HashMap<String, String> data = client.getAll();
        currencies.putAll(data);
    }
}
