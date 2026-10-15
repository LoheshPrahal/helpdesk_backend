package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.Priority;
import com.springboot.helpdesk.enums.Status;

import java.time.Instant;

public record TicketInfoByExecutiveDto(
        long ticketId,
        long executiveId,
        String executiveName,
        String subject,
        Priority priority,
        Status status,
        Instant createdAt,
        long customerId,
        String customerName
) {
}
