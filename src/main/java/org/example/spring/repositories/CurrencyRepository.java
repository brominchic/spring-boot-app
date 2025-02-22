package org.example.spring.repositories;

import org.example.spring.model.entity.CurrencyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<CurrencyEntity, Integer> {
}
