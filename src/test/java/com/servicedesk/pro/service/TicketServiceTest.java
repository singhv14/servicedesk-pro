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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        request.setStatus("OPEN");
        request.setPriority("HIGH");
        request.setCategory("HARDWARE");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Ticket result = ticketService.createTicket(request);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Laptop issue");
        assertThat(result.getDescription()).isEqualTo("Laptop is not starting");
        assertThat(result.getUser()).isEqualTo(user);
        assertThat(result.getStatus()).isEqualTo("OPEN");
        assertThat(result.getPriority()).isEqualTo("HIGH");
        assertThat(result.getCategory()).isEqualTo("HARDWARE");

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

    @Test
    void shouldNormalizeNullSearchFilters() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Ticket> page = new PageImpl<>(List.of());

        when(ticketRepository.searchTickets(
                "",
                "",
                "",
                "",
                pageable
        )).thenReturn(page);

        Page<Ticket> result = ticketService.searchTickets(
                null,
                null,
                null,
                null,
                pageable
        );

        assertThat(result).isSameAs(page);

        verify(ticketRepository).searchTickets(
                "",
                "",
                "",
                "",
                pageable
        );
    }
}