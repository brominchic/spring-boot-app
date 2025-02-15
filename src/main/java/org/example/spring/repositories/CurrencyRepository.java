package org.example.spring.repositories;

import org.example.spring.model.entity.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRepository extends CrudRepository<CurrencyEntity, Integer> {
}
