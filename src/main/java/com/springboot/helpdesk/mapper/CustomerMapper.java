package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.CustomerDto;
import com.springboot.helpdesk.model.Customer;

public class CustomerMapper {
    public static Customer convertDtoToEntity(CustomerDto dto){
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setCity(dto.city());
        customer.setEmail(dto.email());
        return customer;
    }
}
