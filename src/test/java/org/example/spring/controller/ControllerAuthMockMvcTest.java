package org.example.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class ControllerAuthMockMvcTest extends SpringBootApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUnauthorizedAccessToUnsecuredEndpoint() throws Exception {
        mockMvc.perform(get("/unsecured/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("unsecured"));
    }

    @Test
    public void testUnauthorizedAccessToSecuredEndpoint() throws Exception {
        mockMvc.perform(get("/secured/test"))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(username = "brom", password = "$2a$05$w2M8LA8Hl6Bi20yshxjAguh8FFg2aDMKv0Kj71YwEha1Xj9c2gaUC")
    @Test
    public void testAuthorizedAccessToSecuredEndpoint() throws Exception {
        mockMvc.perform(get("/secured/test"))
                .andExpect(status().isOk()).andExpect(content().string("secured"));
    }

    @WithMockUser(username = "brom", password = "$2a$05$w2M8LA8Hl6Bi20yshxjAguh8FFg2aDMKv0Kj71YwEha1Xj9c2gaUC")
    @Test
    public void testAuthorizedAccessToUnsecuredEndpoint() throws Exception {
        mockMvc.perform(get("/unsecured/test"))
                .andExpect(status().isOk()).andExpect(content().string("unsecured"));
    }
}