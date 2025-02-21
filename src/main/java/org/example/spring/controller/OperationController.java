package org.example.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.model.dto.OperationCreateDto;
import org.example.spring.service.component.OperationComponent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OperationController {
    private final OperationComponent component;

    @PostMapping("/unsecured/operation/create")
    public void createNewTransaction(@RequestBody OperationCreateDto input) {
        component.createRow(input.getSum(), input.getCurrency());
    }
}