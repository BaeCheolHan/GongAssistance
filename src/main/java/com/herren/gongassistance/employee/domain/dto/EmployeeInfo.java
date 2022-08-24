package com.herren.gongassistance.employee.domain.dto;

import com.herren.gongassistance.employee.domain.constants.EmployeeStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeInfo {
    private Long id;
    private String name;
    private String tel;
    private String kakaotalkId;
    private EmployeeStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
