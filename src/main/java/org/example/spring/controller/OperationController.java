package org.example.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.service.component.OperationComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class OperationController {
    private final OperationComponent component;

    @GetMapping("unsecured/operation/create")
    public void createNewTransaction(@RequestParam(name = "sum") Long sum, @RequestParam(name = "currency") String currency) {
        component.createRow(sum, currency);
    }
}