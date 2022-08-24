package com.herren.gongassistance.employee.domain.dto;

import com.herren.gongassistance.employee.domain.constants.EmployeeStatus;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeAddRequest {
    @NotNull
    private String name;
    @NotNull
    private String tel;
    private String kakaotalkId;
}
