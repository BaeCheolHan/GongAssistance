package com.herren.gongassistance.employee.domain.entity;

import com.herren.gongassistance.base.entity.BaseTimeEntity;
import com.herren.gongassistance.employee.domain.constants.EmployeeStatus;
import com.herren.gongassistance.shop.domain.entity.Shop;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@SequenceGenerator(name = "EMPLOYEE_SEQ_GENERATOR", sequenceName = "EMPLOYEE_SEQ", initialValue = 1, allocationSize = 1)
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_SEQ_GENERATOR")
    private Long id;

    private String name;
    @Column(unique = true)
    private String tel;
    private String kakaotalkId;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

    @ManyToOne
    private Shop shop;
}
