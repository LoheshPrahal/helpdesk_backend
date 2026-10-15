package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.TicketDto;
import com.springboot.helpdesk.dto.response.TicketRespDto;
import com.springboot.helpdesk.model.Ticket;

public class TicketMapper {
    public static Ticket convertDtoToEntity(TicketDto dto){
        Ticket ticket = new Ticket();
        ticket.setSubject(dto.subject());
        ticket.setIssue(dto.issue());
        ticket.setPriority(dto.priority());
        return ticket;
    }

    public static TicketRespDto convertEntityToDto(Ticket ticket){
        return
                new TicketRespDto (
                        ticket.getId(),
                        ticket.getSubject(),
                        ticket.getCreatedAt(),
                        ticket.getPriority(),
                        ticket.getStatus()
                );
    }
}
