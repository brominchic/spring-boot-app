package org.example.spring.service.component;

import lombok.RequiredArgsConstructor;
import org.example.spring.clients.CurrencyCachedClient;
import org.example.spring.model.entity.OperationEntity;
import org.example.spring.repositories.OperationRepository;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OperationSpringCacheComponent {
    private final OperationRepository repository;
    private final CurrencyCachedComponent component;
    private final CurrencyCachedClient client;

    public void createRowWithCacheFromComponent(Long sum, String currency) {
        String code = component.getCurrencyCode(currency);
        repository.save(OperationEntity.builder()
                .sum(sum)
                .currencyCode(code)
                .build());
    }

    public void createRowWithCacheFromClient(Long sum, String currency) {
        String code = client.getAll().get(currency);
        repository.save(OperationEntity.builder()
                .sum(sum)
                .currencyCode(code)
                .build());
    }

}
