package com.servicedesk.pro.repository;

import com.servicedesk.pro.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("""
    SELECT t
    FROM Ticket t
    WHERE (:status = '' OR t.status = :status)
      AND (:priority = '' OR t.priority = :priority)
      AND (:category = '' OR t.category = :category)
      AND (
          :keyword = ''
          OR LOWER(t.title) LIKE CONCAT('%', LOWER(:keyword), '%')
          OR LOWER(t.description) LIKE CONCAT('%', LOWER(:keyword), '%')
      )
""")
    Page<Ticket> searchTickets(
            @Param("status") String status,
            @Param("priority") String priority,
            @Param("category") String category,
            @Param("keyword") String keyword,
            Pageable pageable
    );

}
