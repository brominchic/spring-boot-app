package org.example.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class ControllerAuthMockMvcTest {

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

    @WithMockUser(username = "user", password = "password")
    @Test
    public void testAuthorizedAccessToSecuredEndpoint() throws Exception {
        mockMvc.perform(get("/secured/test"))
                .andExpect(status().isOk()).andExpect(content().string("secured"));
    }

    @WithMockUser(username = "user", password = "password")
    @Test
    public void testAuthorizedAccessToUnSecuredEndpoint() throws Exception {
        mockMvc.perform(get("/unsecured/test"))
                .andExpect(status().isOk()).andExpect(content().string("unsecured"));
    }
}