package com.springboot.helpdesk.dto.request;

import com.springboot.helpdesk.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketDto(
        @NotBlank(message = "Subject is required")
        @NotNull(message = "Subject is required")
        String subject,
        @NotBlank(message = "Issue is required")
        @NotNull(message = "Issue is required")
        String issue,
        Priority priority
) {
}
