package org.example.spring.service.mapper;

import org.example.spring.model.dto.OperationDto;
import org.example.spring.model.entity.OperationEntity;

public class OperationMapper implements Mapper<OperationDto, OperationEntity> {
    @Override
    public OperationDto entityToDto(OperationEntity entity) {
        return OperationDto.builder().
                id(entity.getId()).
                sum(entity.getSum()).
                currencyCode(entity.getCurrencyCode()).
                build();
    }

    @Override
    public OperationEntity dtoToEntity(OperationDto dto) {
        return OperationEntity.builder().
                id(dto.getId()).
                sum(dto.getSum()).
                currencyCode(dto.getCurrencyCode()).
                build();
    }
}
