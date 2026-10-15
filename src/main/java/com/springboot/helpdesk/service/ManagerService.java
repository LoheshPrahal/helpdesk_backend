package com.springboot.helpdesk.service;

import com.springboot.helpdesk.Exception.ResourceNotFoundException;
import com.springboot.helpdesk.dto.request.ManagerDto;
import com.springboot.helpdesk.dto.response.JobTitleExecutiveCountDto;
import com.springboot.helpdesk.enums.JobTitle;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.mapper.ManagerMapper;
import com.springboot.helpdesk.model.Executive;
import com.springboot.helpdesk.model.Manager;
import com.springboot.helpdesk.model.User;
import com.springboot.helpdesk.repository.ExecutiveRepository;
import com.springboot.helpdesk.repository.ManagerRepository;
import com.springboot.helpdesk.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;
    private final UserRepository userRepository;
    private final ExecutiveRepository executiveRepository;
    private final PasswordEncoder passwordEncoder;

    public Manager add(@Valid ManagerDto managerDto){
        User user = new User();
        user.setUsername(managerDto.username());
        user.setPassword(passwordEncoder.encode(managerDto.password()));
        user.setRole(Role.MANAGER);

        user = userRepository.save(user);

        Manager manager = ManagerMapper.convertDtoToEntity(managerDto);

        manager.setUser(user);

        return managerRepository.save(manager);
    }

    public List<JobTitleExecutiveCountDto> getExecutiveStatWithJobTitleAndCount() {
        return managerRepository.getExecutiveStatWithJobTitleAndCount();
    }

    public void updateJobTitle(long executiveId, JobTitle jobTitle) {
        Executive executive = executiveRepository.findById(executiveId)
                .orElseThrow(()-> new ResourceNotFoundException("Executive Not Found"));

        executive.setJobTitle(jobTitle);

        executiveRepository.save(executive);

    }
}
