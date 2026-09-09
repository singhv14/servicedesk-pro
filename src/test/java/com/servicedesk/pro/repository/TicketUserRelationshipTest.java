package com.servicedesk.pro.repository;

import com.servicedesk.pro.entity.Ticket;
import com.servicedesk.pro.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TicketUserRelationshipTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void shouldSaveTicketWithUser() {

        // Create a User
        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");

        User savedUser = userRepository.save(user);

        // Create a Ticket
        Ticket ticket = new Ticket();
        ticket.setTitle("Laptop issue");
        ticket.setDescription("Laptop is not starting");

        // Connect Ticket to User
        ticket.setUser(savedUser);

        // Save Ticket
        Ticket savedTicket = ticketRepository.save(ticket);

        // Verify Ticket was saved
        assertThat(savedTicket.getId()).isNotNull();

        // Verify User relationship
        assertThat(savedTicket.getUser()).isNotNull();
        assertThat(savedTicket.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(savedTicket.getUser().getName()).isEqualTo("John");
        
    }
}