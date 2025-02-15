package org.example.spring.service.component;

import lombok.RequiredArgsConstructor;
import org.example.spring.model.dto.CurrencyDto;
import org.example.spring.model.entity.CurrencyEntity;
import org.example.spring.repositories.CurrencyRepository;
import org.example.spring.service.mapper.CurrencyMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class SlowComponent {

    private final CurrencyRepository repository;
    private final CurrencyMapper mapper;

    public HashMap<String, String> getAll() {
        ArrayList<CurrencyDto> dtoList = new ArrayList<>();
        ArrayList<CurrencyEntity> entityList = new ArrayList<>();
        repository.findAll().forEach(entityList::add);
        for (CurrencyEntity currencyEntity : entityList) {
            dtoList.add(mapper.entityToDto(currencyEntity));
        }
        HashMap<String, String> currenciesCodes = new HashMap<>();
        for (CurrencyDto currencyDto : dtoList) {
            currenciesCodes.put(currencyDto.getName(), String.valueOf(currencyDto.getCode()));
        }
        return currenciesCodes;
    }
}
