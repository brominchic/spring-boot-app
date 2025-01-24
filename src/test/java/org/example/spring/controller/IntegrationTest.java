package org.example.spring.controller;

import org.example.spring.model.entity.UserEntity;
import org.example.spring.repositories.RoleRepository;
import org.example.spring.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationTest extends SpringBootApplicationTest {
    @LocalServerPort
    private final Integer port = 9999;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    void test() {

        UserEntity userEntity = UserEntity.builder().
                id(1L).
                login("brom").password("password").role(1L).
                build();
        userRepository.save(userEntity);
    }

}

