package org.example.spring.repositories;

import org.example.spring.model.entity.OperationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<OperationEntity, Integer> {
}
