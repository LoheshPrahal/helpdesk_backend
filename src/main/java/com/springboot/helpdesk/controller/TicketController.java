package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketInfoByExecutiveDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketNumbersByStatusDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/api/ticket/add/{customerId}")
    public void postTicket(Principal principal, @Valid @RequestBody TicketDto ticketDto){
        ticketService.add(principal.getName(), ticketDto);
    }

    @PutMapping("/api/ticket/assign/{executiveId}/{ticketId}")
    public void assignExecutive(@PathVariable long executiveId, @PathVariable long ticketId, Principal principal){
        ticketService.assignExecutive(executiveId, ticketId, principal.getName());
    }

    @GetMapping("/api/ticket/v1/{customerId}")
    public List<TicketInfoDto> getTicketByCustomerId(@PathVariable Long customerId, @RequestParam("page") int page, @RequestParam("size") int size){
        return ticketService.getTicketByCustomerId(customerId, page, size);
    }

    @GetMapping("/api/ticket/v2")
    public List<TicketInfoDto> getTicketByCustomerUsername(@RequestParam("username") String customerUsername,
                                            @RequestParam(name = "page" , required = false, defaultValue = "0") Integer page,
                                            @RequestParam(name = "size", required = false, defaultValue = "20") Integer size){
        return ticketService.getTicketByCustomerUsername(customerUsername, page, size);
    }

    @GetMapping("/api/ticket/executive")
    public List<TicketInfoByExecutiveDto> getTicketByExecutiveUsername(@RequestParam("username") String executiveUsername,
                                                                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                       @RequestParam(name = "size", required = false, defaultValue = "20") Integer size){
        return ticketService.getTicketByExecutiveUsername(executiveUsername, page, size);
    }

    @GetMapping("/api/ticket/status/customers/stat")
    public List<TicketNumbersByStatusDto> getTicketNumbersByStatus(){
        return ticketService.getTicketNumbersByStatus();
    }

    @GetMapping("/api/ticket/by-priority")
    public List<TicketRespDto> getTicketsByPriority(@RequestParam("priority") Priority priority){
        return ticketService.getTicketsByPriority(priority);
    }

    @GetMapping("/api/ticket/filter")
    public List<TicketRespDto> dynamicFilterTicket(@RequestParam(value = "priority", required = false) Priority priority,
                                                   @RequestParam(value = "status", required = false) Status status,
                                                   @RequestParam(value = "fromDate", required = false) LocalDate fromDate,
                                                   @RequestParam(value = "toDate", required = false) LocalDate toDate){
        return ticketService.dynamicFilterTicket(priority, status, fromDate, toDate);
    }
}
