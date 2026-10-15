package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.ExecutiveDto;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.service.ExecutiveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class ExecutiveController {
    private final ExecutiveService executiveService;

    @PostMapping("/api/executive/add")
    public Executive insertExecutive(@Valid @RequestBody ExecutiveDto executiveDto, Principal principal){
        return executiveService.add(executiveDto, principal.getName());
    }


}
