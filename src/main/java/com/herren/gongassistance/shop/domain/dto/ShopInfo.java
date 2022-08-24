package com.herren.gongassistance.shop.domain.dto;

import com.herren.gongassistance.employee.domain.dto.EmployeeInfo;
import com.herren.gongassistance.employee.domain.entity.Employee;
import com.herren.gongassistance.shop.domain.constants.ShopStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopInfo {
    private Long id;
    private String shopName;
    private String bizNo;
    private String tel;
    private String kakaotalkId;
    private ShopStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<EmployeeInfo> employees;
}
