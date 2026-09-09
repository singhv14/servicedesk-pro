package com.servicedesk.pro.service;

import com.servicedesk.pro.dto.TicketRequest;
import com.servicedesk.pro.entity.Ticket;
import com.servicedesk.pro.entity.User;
import com.servicedesk.pro.repository.TicketRepository;
import com.servicedesk.pro.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void shouldCreateTicketForExistingUser() {

        User user = new User();
        user.setName("John");
        user.setEmail("john@example.com");

        TicketRequest request = new TicketRequest();
        request.setTitle("Laptop issue");
        request.setDescription("Laptop is not starting");
        request.setUserId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Ticket savedTicket = new Ticket();
        savedTicket.setTitle("Laptop issue");
        savedTicket.setDescription("Laptop is not starting");
        savedTicket.setUser(user);

        when(ticketRepository.save(any(Ticket.class)))
                .thenReturn(savedTicket);

        Ticket result = ticketService.createTicket(request);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Laptop issue");
        assertThat(result.getDescription()).isEqualTo("Laptop is not starting");
        assertThat(result.getUser()).isEqualTo(user);

        verify(userRepository).findById(1L);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {

        TicketRequest request = new TicketRequest();
        request.setTitle("Laptop issue");
        request.setDescription("Laptop is not starting");
        request.setUserId(999L);

        when(userRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> ticketService.createTicket(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");

        verify(ticketRepository, never()).save(any(Ticket.class));
    }
}