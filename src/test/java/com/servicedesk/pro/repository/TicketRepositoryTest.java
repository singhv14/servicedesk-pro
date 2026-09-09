package com.servicedesk.pro.repository;

import com.servicedesk.pro.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TicketRepositoryTest {

    @Autowired
    private   TicketRepository ticketRepository;

    @Test
    void shouldSaveAndReadTicket(){

        Ticket ticket = new Ticket();

        Ticket savedTicket = ticketRepository.save(ticket);

        assertThat(savedTicket.getId()).isNotNull();

        Optional<Ticket> foundTicket = ticketRepository.findById(savedTicket.getId());

        assertThat(foundTicket).isPresent();


    }

}
