package org.example.spring.service.mapper;

import lombok.RequiredArgsConstructor;
import org.example.spring.model.dto.CurrencyDto;
import org.example.spring.model.entity.CurrencyEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyMapper implements Mapper<CurrencyDto, CurrencyEntity> {

    @Override
    public CurrencyDto entityToDto(CurrencyEntity entity) {
        return CurrencyDto.builder().
                code(entity.getCode()).
                name(entity.getName()).
                id(entity.getId()).
                build();
    }

    @Override
    public CurrencyEntity dtoToEntity(CurrencyDto dto) {
        return CurrencyEntity.builder().
                code(dto.getCode()).
                name(dto.getName()).
                id(dto.getId()).
                build();
    }

}
