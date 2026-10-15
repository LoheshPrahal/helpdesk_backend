package com.springboot.helpdesk.service;

import com.springboot.helpdesk.dto.request.CustomerPlanDto;
import com.springboot.helpdesk.mapper.CustomerPlanMapper;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.CustomerPlan;
import com.springboot.helpdesk.model.Plan;
import com.springboot.helpdesk.repository.CustomerPlanRepository;
import com.springboot.helpdesk.repository.PlanRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerPlanService {
    private final CustomerPlanRepository customerPlanRepository;
    private final CustomerService customerService;
    private final PlanRepository planRepository;
    private final PlanService planService;

    public void insertCustomerPlan(Long customerId, Long planId, @Valid CustomerPlanDto customerPlanDto) {
        Customer customer = customerService.getById(customerId);
        CustomerPlan customerPlan = CustomerPlanMapper.convertDtoToEntity(customerPlanDto);
        customerPlan.setCustomer(customer);
        customerPlan.setEndDate(customerPlan.getActivationDate().atStartOfDay().toLocalDate().plusDays(30));
        Plan plan = planService.getPlanById(planId);
        customerPlan.setPlan(plan);
        customerPlanRepository.save(customerPlan);
    }
}
