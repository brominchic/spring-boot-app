package org.example.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.service.component.SlowComponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SlowController {
    private final SlowComponent component;

    @GetMapping("unsecured/slow/test")
    public HashMap<String, String> getAll() throws InterruptedException {
        Thread.sleep(5000);
        return component.getAll();
    }
}
