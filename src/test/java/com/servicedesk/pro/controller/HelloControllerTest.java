package com.servicedesk.pro.controller;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

//    @Test
//    void shouldCreateTicket() throws Exception {
//
//        mockMvc.perform(
//                        post("/api/tickets")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content("""
//                        {
//                            "title": "Laptop issue",
//                            "description": "Laptop is not starting"
//                        }
//                        """)
//                )
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.title").value("Laptop issue"))
//                .andExpect(jsonPath("$.description").value("Laptop is not starting"));
//
//
//    }

}
