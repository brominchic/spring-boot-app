package org.example.spring.service.component;

import lombok.RequiredArgsConstructor;
import org.example.spring.clients.CurrencyClient;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.OperationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OperationSpringCacheComponent {
    private final CurrencyClient client;
    private final OperationRepository repository;

    public void createRowWithCache(Long sum, String currency) {
        String code = getCurrencyCode(currency);
        repository.save(OperationEntity.
                builder().
                sum(sum).
                currencyCode(code)
                .build());
    }

    @Cacheable(cacheNames = "codes", key = "#currency")
    private String getCurrencyCode(String currency) {
        return client.getAll().get(currency);
    }
}
