package com.servicedesk.pro.service;

import com.servicedesk.pro.dto.TicketRequest;
import com.servicedesk.pro.entity.Ticket;
import com.servicedesk.pro.entity.User;
import com.servicedesk.pro.exception.UserNotFoundException;
import com.servicedesk.pro.repository.TicketRepository;
import com.servicedesk.pro.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket createTicket(TicketRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Ticket ticket = new Ticket();
        
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setUser(user);
        ticket.setStatus(request.getStatus());
        ticket.setPriority(request.getPriority());
        ticket.setCategory(request.getCategory());

        return ticketRepository.save(ticket);

    }

    public Page<Ticket> searchTickets(
            String status,
            String priority,
            String category,
            String keyword,
            Pageable pageable) {

        return ticketRepository.searchTickets(
                status == null ? "" : status,
                priority == null ? "" : priority,
                category == null ? "" : category,
                keyword == null ? "" : keyword,
                pageable
        );
    }

}
