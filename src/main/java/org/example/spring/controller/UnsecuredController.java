package org.example.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.service.component.SlowComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/unsecured")
public class UnsecuredController {

    private final SlowComponent component;

    @GetMapping("/test")
    public String unsecuredTest() {
        return "unsecured";
    }

    @GetMapping("/slow/test")
    public HashMap<String, String> getAll() throws InterruptedException {
        return component.getCurrencies();
    }
}
