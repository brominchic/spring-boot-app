package org.example.spring.service.component;

import lombok.RequiredArgsConstructor;
import org.example.spring.clients.CurrencyClient;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.OperationRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OperationComponent {
    private final CurrencyClient client;
    private final OperationRepository repository;

    public void createRow(Long sum, String currency) {
        String code = client.getAll().get(currency);
        repository.save(OperationEntity.
                builder().
                sum(sum).
                currencyCode(code)
                .build());
    }
}
