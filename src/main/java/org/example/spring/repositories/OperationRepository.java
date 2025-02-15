package org.example.spring.repositories;

import org.example.spring.model.entity.OperationEntity;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepository extends CrudRepository<OperationEntity, Integer> {
}
