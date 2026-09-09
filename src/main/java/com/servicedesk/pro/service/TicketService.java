package com.servicedesk.pro.service;

import com.servicedesk.pro.dto.TicketRequest;
import com.servicedesk.pro.entity.Ticket;
import com.servicedesk.pro.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(TicketRequest request) {

        Ticket ticket = new Ticket();
        
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());

        return ticketRepository.save(ticket);

    }

}
