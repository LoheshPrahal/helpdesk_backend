package com.springboot.helpdesk.service;

import com.springboot.helpdesk.Exception.ResourceNotFoundException;
import com.springboot.helpdesk.dto.request.ExecutiveDto;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.mapper.ExecutiveMapper;
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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExecutiveService {
    private final ExecutiveRepository executiveRepository;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;


    public Executive add(@Valid ExecutiveDto executiveDto, String managerUsername) {
        Manager manager = managerRepository.getByUsername(managerUsername);

        User user = new User();
        user.setUsername(executiveDto.username());
        user.setPassword(executiveDto.password());
        user.setRole(Role.EXECUTIVE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        Executive executive = ExecutiveMapper.mapDtoToEntity(executiveDto);

        executive.setUser(user);
        executive.setManager(manager);

        return executiveRepository.save(executive);
    }
}
