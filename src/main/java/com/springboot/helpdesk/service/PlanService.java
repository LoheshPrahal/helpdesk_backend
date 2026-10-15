package com.springboot.helpdesk.service;

import com.springboot.helpdesk.Exception.ResourceNotFoundException;
import com.springboot.helpdesk.model.Plan;
import com.springboot.helpdesk.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public Plan getPlanById(long id){
        return planRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Invalid Plan Id!!!"));
    }
}
