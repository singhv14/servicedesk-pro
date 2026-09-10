package com.servicedesk.pro.controller;

import com.servicedesk.pro.dto.TicketRequest;
import com.servicedesk.pro.entity.Ticket;
import com.servicedesk.pro.exception.UserNotFoundException;
import com.servicedesk.pro.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TicketService ticketService;

    @Test
    void shouldCreateTicket() throws Exception {

        Ticket ticket = new Ticket();
        ticket.setTitle("Laptop issue");
        ticket.setDescription("Laptop is not starting");

        when(ticketService.createTicket(any(TicketRequest.class))).thenReturn(ticket);

        mockMvc.perform(post("/api/tickets").contentType(MediaType.APPLICATION_JSON).content("""
                    {
                         "title": "Laptop issue",
                         "description": "Laptop is not starting",
                         "userId": 1
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Laptop issue"))
                .andExpect(jsonPath("$.description").value("Laptop is not starting"));

    }

    @Test
    void shouldRejectBlankTitle() throws Exception {

        mockMvc.perform(
                        post("/api/tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "title": "",
                                "description": "Laptop is not starting",
                                "userId": 1
                            }
                            """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectMissingUserId() throws Exception {

        mockMvc.perform(
                        post("/api/tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "title": "",
                                "description": "Laptop is not starting"
                            }
                            """)
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldRejectDescriptionTooLong() throws Exception {

        mockMvc.perform(
                        post("/api/tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "title": "",
                                "description": "This description is intentionally made longer than one hundred characters so that validation should reject this request.",
                                "userId": 1
                            }
                            """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn404WhenUserDoesNotExist() throws Exception {

        given(ticketService.createTicket(any(TicketRequest.class)))
                .willThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(
                        post("/api/tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                                "title": "Laptop issue",
                                "description": "Laptop is not starting",
                                "userId": 999
                            }
                            """)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturnPaginatedTickets() throws Exception {
        Pageable pageable = PageRequest.of(0, 2);

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        Page<Ticket> page = new PageImpl<>(
                List.of(ticket1, ticket2),
                pageable,
                4
        );

        when(ticketService.getTickets(any(Pageable.class)))
                .thenReturn(page);

        mockMvc.perform(get("/api/tickets")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.number").value(0))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalElements").value(4))
                .andExpect(jsonPath("$.totalPages").value(2));
    }



}
