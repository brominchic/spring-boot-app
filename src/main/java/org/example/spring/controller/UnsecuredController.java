package org.example.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.model.dto.OperationCreateDto;
import org.example.spring.service.component.OperationComponent;
import org.example.spring.service.component.SlowComponent;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/unsecured")
public class UnsecuredController {

    private final SlowComponent currencyComponent;
    private final OperationComponent operationComponent;

    @GetMapping("/test")
    public String unsecuredTest() {
        return "unsecured";
    }

    @GetMapping("/slow/test")
    public HashMap<String, String> getAll() throws InterruptedException {
        return currencyComponent.getCurrencies();
    }

    @PostMapping("/operation/create")
    public void createNewTransaction(@RequestBody OperationCreateDto input) {
        operationComponent.createRow(input.getSum(), input.getCurrency());
    }
}
