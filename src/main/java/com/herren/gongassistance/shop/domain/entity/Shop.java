package com.herren.gongassistance.shop.domain.entity;

import com.herren.gongassistance.base.entity.BaseTimeEntity;
import com.herren.gongassistance.employee.domain.entity.Employee;
import com.herren.gongassistance.shop.domain.constants.ShopStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table
@Getter
@Setter
@SequenceGenerator(name = "SHOP_SEQ_GENERATOR", sequenceName = "SHOP_SEQ", initialValue = 1, allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Shop extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHOP_SEQ_GENERATOR")
    private Long id;

    private String shopName;
    @Column(unique = true)
    private String bizNo;
    private String tel;
    private String kakaotalkId;

    @Enumerated(EnumType.STRING)
    private ShopStatus status;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Employee> employees;


}
