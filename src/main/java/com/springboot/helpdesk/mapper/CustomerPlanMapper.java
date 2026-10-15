package com.springboot.helpdesk.mapper;

import com.springboot.helpdesk.dto.request.CustomerPlanDto;
import com.springboot.helpdesk.model.CustomerPlan;

public class CustomerPlanMapper {
    public static CustomerPlan convertDtoToEntity(CustomerPlanDto customerPlanDto){
        CustomerPlan customerPlan = new CustomerPlan();
        customerPlan.setCoupon(customerPlanDto.coupon());
        customerPlan.setAmountPaid(customerPlanDto.amountPaid());
        return customerPlan;
    }
}
