package com.springboot.helpdesk.service;

import com.springboot.helpdesk.Exception.InvalidCallException;
import com.springboot.helpdesk.Exception.ResourceNotFoundException;
import com.springboot.helpdesk.dto.request.CustomerDto;
import com.springboot.helpdesk.dto.request.CustomerUpdateDto;
import com.springboot.helpdesk.enums.Role;
import com.springboot.helpdesk.mapper.CustomerMapper;
import com.springboot.helpdesk.model.Customer;
import com.springboot.helpdesk.model.User;
import com.springboot.helpdesk.repository.CustomerRepository;
import com.springboot.helpdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public void insertCustomer(CustomerDto dto){
        User user = userService.getUserObj(
                dto.username(),
                dto.password(),
                Role.CUSTOMER
        );

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Customer customer = CustomerMapper.convertDtoToEntity(dto);

        customer.setUser(user);

        customerRepository.save(customer);
    }

    public Customer getById(long id){
        Optional<Customer> optional = customerRepository.findById(id);
        if(optional.isEmpty()) throw new ResourceNotFoundException("Invalid customer id!!!");

        return optional.get();
    }

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public void deleteById(long id){
        customerRepository.deleteById(id);
    }

    public void update(long id, CustomerUpdateDto dto) {
        Customer customer = getById(id);
        boolean flag = false;

        if(!(dto.name() == null ) && !(dto.name().isEmpty())){
            customer.setName(dto.name());
            flag = true;
        }

        if(!(dto.city() == null) && !(dto.city().isEmpty())){
            customer.setCity(dto.city());
            flag = true;
        }

        if(!flag) throw new InvalidCallException("Update operation terminated");

        customerRepository.save(customer);
    }
}
