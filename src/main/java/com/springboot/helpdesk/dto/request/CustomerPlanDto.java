package com.springboot.helpdesk.dto.request;

import com.springboot.helpdesk.model.Plan;

public record CustomerPlanDto (
        String coupon,
        double amountPaid
){
}
