package org.example.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.spring.model.dto.OperationCreateDto;
import org.example.spring.service.component.OperationComponent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/secured")
public class SecuredController {


    @GetMapping("/test")
    @PreAuthorize("hasAuthority('user')")
    public String securedTest() {
        return "secured";
    }


}
