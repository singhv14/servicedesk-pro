package com.servicedesk.pro.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpointShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/api/hello")).andExpect(status().isOk());
    }

    @Test
    void publicEndpointShouldBeUnauthorized() throws Exception {
        mockMvc.perform(get("/api/tickets/**")).andExpect(status().isForbidden());
    }
}
