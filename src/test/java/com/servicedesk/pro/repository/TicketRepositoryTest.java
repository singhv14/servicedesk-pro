package com.servicedesk.pro.repository;

import com.servicedesk.pro.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
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


    @Test
    void shouldFindTicketsByStatus() {

        ticketRepository.deleteAll();

        Ticket openTicket = new Ticket();
        openTicket.setTitle("Laptop issue");
        openTicket.setDescription("Laptop is not starting");
        openTicket.setStatus("OPEN");
        openTicket.setPriority("HIGH");
        openTicket.setCategory("HARDWARE");

        Ticket closedTicket = new Ticket();
        closedTicket.setTitle("Printer issue");
        closedTicket.setDescription("Printer is not working");
        closedTicket.setStatus("CLOSED");
        closedTicket.setPriority("LOW");
        closedTicket.setCategory("HARDWARE");

        ticketRepository.save(openTicket);
        ticketRepository.save(closedTicket);

        Page<Ticket> result = ticketRepository.searchTickets(
                "OPEN",
                "",
                "",
                "",
                PageRequest.of(0, 10)
        );

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getStatus()).isEqualTo("OPEN");
    }


    @Test
    void shouldFindTicketsByKeyword() {

        ticketRepository.deleteAll();

        Ticket laptopTicket = new Ticket();
        laptopTicket.setTitle("Laptop issue");
        laptopTicket.setDescription("Laptop is not starting");
        laptopTicket.setStatus("OPEN");
        laptopTicket.setPriority("HIGH");
        laptopTicket.setCategory("HARDWARE");

        Ticket printerTicket = new Ticket();
        printerTicket.setTitle("Printer issue");
        printerTicket.setDescription("Printer is not working");
        printerTicket.setStatus("OPEN");
        printerTicket.setPriority("LOW");
        printerTicket.setCategory("HARDWARE");

        ticketRepository.save(laptopTicket);
        ticketRepository.save(printerTicket);

        Page<Ticket> result = ticketRepository.searchTickets(
                "",
                "",
                "",
                "laptop",
                PageRequest.of(0, 10)
        );

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Laptop issue");
    }


    @Test
    void shouldFindTicketsByMultipleFilters() {

        ticketRepository.deleteAll();

        Ticket matchingTicket = new Ticket();
        matchingTicket.setTitle("Laptop issue");
        matchingTicket.setDescription("Laptop is not starting");
        matchingTicket.setStatus("OPEN");
        matchingTicket.setPriority("HIGH");
        matchingTicket.setCategory("HARDWARE");

        Ticket nonMatchingTicket = new Ticket();
        nonMatchingTicket.setTitle("Printer issue");
        nonMatchingTicket.setDescription("Printer is not working");
        nonMatchingTicket.setStatus("OPEN");
        nonMatchingTicket.setPriority("LOW");
        nonMatchingTicket.setCategory("HARDWARE");

        ticketRepository.save(matchingTicket);
        ticketRepository.save(nonMatchingTicket);

        Page<Ticket> result = ticketRepository.searchTickets(
                "OPEN",
                "HIGH",
                "HARDWARE",
                "laptop",
                PageRequest.of(0, 10)
        );

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Laptop issue");
    }

}
