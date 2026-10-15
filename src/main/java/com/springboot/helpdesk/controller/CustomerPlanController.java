package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.CustomerPlanDto;
import com.springboot.helpdesk.service.CustomerPlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerPlanController {
    private final CustomerPlanService customerPlanService;

    @PostMapping("/api/customerplan/add/{customerId}/{planId}")
    public void insertCustomerPlan(@PathVariable long customerId, @PathVariable long planId, @Valid @RequestBody CustomerPlanDto customerPlanDto){
        customerPlanService.insertCustomerPlan(customerId, planId, customerPlanDto);
    }
}
