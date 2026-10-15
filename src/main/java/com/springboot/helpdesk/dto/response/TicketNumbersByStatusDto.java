package com.springboot.helpdesk.dto.response;

import com.springboot.helpdesk.enums.Status;

public record TicketNumbersByStatusDto(
        Status status,
        long numberOfCustomers
) {
}
