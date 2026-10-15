package com.springboot.helpdesk.service;

import com.springboot.helpdesk.Exception.OwnershipException;
import com.springboot.helpdesk.Exception.ResourceNotFoundException;
import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketInfoByExecutiveDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.dto.response.TicketNumbersByStatusDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.mapper.TicketMapper;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.model.Ticket;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.ExecutiveRepository;
import com.springboot.helpdesk.repository.TicketRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final CustomerRepository customerRepository;
    private final TicketRepository ticketRepository;
    private final ExecutiveRepository executiveRepository;

    public void add(String customerUsername, @Valid TicketDto ticketDto) {
        Customer customer =  customerRepository.getByUsername(customerUsername);

        Ticket ticket = TicketMapper.convertDtoToEntity(ticketDto);

        ticket.setCustomer(customer);
        ticket.setStatus(Status.OPEN);
        ticketRepository.save(ticket);
    }

    public void assignExecutive(long executiveId, long ticketId, String managerUsername) {
        Executive executive = executiveRepository.findById(executiveId)
                .orElseThrow(() -> new ResourceNotFoundException("Executive not found!!!"));

        if(! (executive.getManager().getUser().getUsername().equals(managerUsername))){
            throw new OwnershipException("Operation not allowed! contact admin");
        }

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found!!!"));

        ticket.setExecutive(executive);


        ticketRepository.save(ticket);
    }

    public List<TicketInfoDto> getTicketByCustomerId(Long customerId, int page, int size) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Id Invalid"));
        Pageable pageable = PageRequest.of(page, size);
        return ticketRepository.getTicketByCustomerId(customerId, pageable);
    }

    public List<TicketInfoDto> getTicketByCustomerUsername(String customerUsername, Integer page, Integer size) {
        Pageable pageable =  PageRequest.of(page,size);
        return ticketRepository.getTicketByCustomerUsername(customerUsername,pageable);
    }

    public List<TicketInfoByExecutiveDto> getTicketByExecutiveUsername(String executiveUsername, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return ticketRepository.getTicketByExecutiveUsername(executiveUsername, pageable);
    }

    public List<TicketNumbersByStatusDto> getTicketNumbersByStatus() {
        return ticketRepository.getTicketNumbersByStatus();
    }

    public List<TicketRespDto> getTicketsByPriority(Priority priority) {
        List<Ticket> list = ticketRepository.findByPriority(priority);
        return list
                .stream()
                .map(TicketMapper :: convertEntityToDto)
                .toList();
    }

    public List<TicketRespDto> dynamicFilterTicket(Priority priority, Status status, LocalDate fromDate, LocalDate toDate) {
        return ticketRepository.dynamicFilterTicket(priority, status, fromDate == null ? null : fromDate.atStartOfDay(ZoneOffset.UTC).toInstant(),
                toDate == null ? null : toDate.atTime(11, 59).toInstant(ZoneOffset.UTC));
    }
}
