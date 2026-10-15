package com.springboot.helpdesk.controller;

import com.springboot.helpdesk.dto.request.ManagerDto;
import com.springboot.helpdesk.dto.response.JobTitleExecutiveCountDto;
import com.springboot.helpdesk.enums.JobTitle;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.model.Manager;
import com.springboot.helpdesk.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/api/manager/add")
    public void add(@Valid @RequestBody ManagerDto managerDto){
        managerService.add(managerDto);
    }


    @GetMapping("/api/manager/jobtitle/stat/executive/count")
    public List<JobTitleExecutiveCountDto> getExecutiveStatWithJobTitleAndCount(){
        return managerService.getExecutiveStatWithJobTitleAndCount();
    }

    @PutMapping("/api/jobtitle/update/{executiveId}")
    public void updateJobTitle(@PathVariable long executiveId,
                               @RequestParam("jobTitle") JobTitle jobTitle){
        managerService.updateJobTitle(executiveId,jobTitle);
    }
}
