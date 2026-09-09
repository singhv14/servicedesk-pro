package com.servicedesk.pro.repository;

import com.servicedesk.pro.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
