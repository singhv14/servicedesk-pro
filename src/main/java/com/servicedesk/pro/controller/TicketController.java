package com.servicedesk.pro.controller;

import com.servicedesk.pro.dto.TicketRequest;
import com.servicedesk.pro.entity.Ticket;
import com.servicedesk.pro.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(
            @Valid @RequestBody TicketRequest ticketRequest) {

        Ticket createdTicket = ticketService.createTicket(ticketRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTicket);
    }

    @GetMapping
    public ResponseEntity<Page<Ticket>> getTickets(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            Pageable pageable) {

        return ResponseEntity.ok(
                ticketService.searchTickets(
                        status,
                        priority,
                        category,
                        keyword,
                        pageable
                )
        );
    }
}