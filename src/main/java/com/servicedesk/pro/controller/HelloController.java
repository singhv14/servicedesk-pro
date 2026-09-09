package com.servicedesk.pro.controller;

import com.servicedesk.pro.dto.TicketRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from ServiceDesk Pro";
    }


//    @PostMapping("/tickets")
//    public ResponseEntity<TicketRequest> createTicket(@RequestBody TicketRequest ticketRequest) {
//        return new ResponseEntity<>(ticketRequest, HttpStatus.CREATED);
//    }

}
