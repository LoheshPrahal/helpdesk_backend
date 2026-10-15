package com.springboot.helpdesk.service;

import com.springboot.helpdesk.Exception.ResourceNotFoundException;
import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketInfoDto;
import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.Ticket;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.TicketRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Test
    public void getTicketsByCustomerIdTest(){
        TicketInfoDto dto1 = new TicketInfoDto(1L, "test1", Instant.now(), Priority.LOW, Status.OPEN, "Tony Stark", "Hulk", "hulk@gmail.com");
        TicketInfoDto dto2 = new TicketInfoDto(2L, "test2", Instant.now(), Priority.MEDIUM, Status.CLOSED, "Bruce", "Thor", "thor@gmail.com");
        TicketInfoDto dto3 = new TicketInfoDto(3L, "test3", Instant.now(), Priority.HIGH, Status.OPEN, "Captain", "Peter", "peter@gmail.com");

        Customer customer1 = new Customer();
        customer1.setId(1L);

        Customer customer2 = new Customer();
        customer2.setId(2L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));

        when(ticketRepository.getTicketByCustomerId(1L, PageRequest.of(0, 5)))
                .thenReturn(List.of(dto1, dto2));

        when(ticketRepository.getTicketByCustomerId(2L, PageRequest.of(0, 5)))
                .thenReturn(List.of(dto3));

        assertEquals(2, ticketService.getTicketByCustomerId(1L, 0, 5).size());
        assertEquals(1, ticketService.getTicketByCustomerId(2L, 0, 5).size());

        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).findById(2L);

        verify(ticketRepository, times(1))
                .getTicketByCustomerId(1L,PageRequest.of(0,5));
        verify(ticketRepository, times(1))
                .getTicketByCustomerId(2L,PageRequest.of(0,5));

        verifyNoMoreInteractions(customerRepository);
        verifyNoMoreInteractions(ticketRepository);

    }

    @Test
    public void getTicketsByCustomerIdTest_Invalid() {
        when(customerRepository.findById(5L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class,
                ()-> ticketService.getTicketByCustomerId(5L, 0, 5));

        verify(customerRepository, times(1)).findById(5L);

        verify(ticketRepository, times(0))
                .getTicketByCustomerId(1L, PageRequest.of(0, 5));

        verifyNoMoreInteractions(customerRepository);
        verifyNoMoreInteractions(ticketRepository);
    }

    @Test
    public void saveTest(){
        String customerUsername = "Thor";
        TicketDto ticketDto = new TicketDto("testSubject", "testIssue", Priority.HIGH);

        Customer customer = new Customer();
        when(customerRepository.getByUsername(customerUsername)).thenReturn(customer);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(any(Ticket.class));

        ticketService.add(customerUsername, ticketDto);

        verify(customerRepository, times(1)).getByUsername(customerUsername);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }
}
