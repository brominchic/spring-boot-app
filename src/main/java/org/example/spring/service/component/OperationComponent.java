package org.example.spring.service.component;

import lombok.RequiredArgsConstructor;
import org.example.spring.clients.CurrencyClient;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.OperationRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class OperationComponent {
    private final CurrencyClient client;
    private final OperationRepository repository;
    private ConcurrentHashMap<String, String> currencies;

    public void createRow(Long sum, String currency) {
        if (currencies == null) {
            fillTheMap();
        }
        String code = currencies.get(currency);
        repository.save(OperationEntity.
                builder().
                sum(sum).
                currencyCode(code)
                .build());
    }

    private void fillTheMap() {
        currencies = new ConcurrentHashMap<>(client.getAll());
    }
}
