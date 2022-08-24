package com.herren.gongassistance.employee.domain.dto;

import com.herren.gongassistance.employee.domain.constants.EmployeeStatus;
import com.herren.gongassistance.shop.domain.constants.ShopStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequest {
    @NotNull
    private EmployeeStatus status;
}
