package com.servicedesk.pro.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHelloMessage() throws Exception {

        mockMvc.perform(get("/api/hello")) //Pretend a client sent GET /api/hello.
                .andExpect(status().isOk()) //I expect HTTP 200 OK.
                .andExpect(content().string("Hello from ServiceDesk Pro")); //I expect the response body to exactly match the controller's response.

    }

}
