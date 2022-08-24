package com.herren.gongassistance.employee.service;

import com.herren.gongassistance.base.exception.GongAssistanceCode;
import com.herren.gongassistance.base.exception.GongAssistanceException;
import com.herren.gongassistance.base.lock.RedisLockService;
import com.herren.gongassistance.data.EmployeeDataService;
import com.herren.gongassistance.data.ShopDataService;
import com.herren.gongassistance.employee.domain.constants.EmployeeStatus;
import com.herren.gongassistance.employee.domain.dto.EmployeeAddRequest;
import com.herren.gongassistance.employee.domain.dto.EmployeeUpdateRequest;
import com.herren.gongassistance.employee.domain.entity.Employee;
import com.herren.gongassistance.shop.domain.constants.ShopStatus;
import com.herren.gongassistance.shop.domain.entity.Shop;
import com.herren.gongassistance.utils.LockKeyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeDataService employeeDataService;
    private final ShopDataService shopDataService;

    private final RedisLockService redisLockService;

    public void addEmployee(EmployeeAddRequest employeeAddRequest) {

        redisLockService.runWithLock(LockKeyFactory.createEmployeeKey(employeeAddRequest.getTel()), 1000L, () -> {
            employeeDataService.findByTel(employeeAddRequest.getTel())
                    .ifPresent(it -> {
                        throw new GongAssistanceException(GongAssistanceCode.E0008);
                    });
        });

        employeeDataService.save(Employee.builder()
                .name(employeeAddRequest.getName())
                .status(EmployeeStatus.NORMAL)
                .tel(employeeAddRequest.getTel())
                .kakaotalkId(employeeAddRequest.getKakaotalkId())
                .build());
    }

    public void updateEmployeeStatus(Long employeeId, EmployeeUpdateRequest request) {
        Employee employeeEntity = employeeDataService.findById(employeeId);

        if(request.getStatus().equals(EmployeeStatus.DELETE)) {
            employeeEntity.setShop(null);
            employeeEntity.setName(null);
            employeeEntity.setTel(null);
            employeeEntity.setKakaotalkId(null);
        }

        employeeEntity.setStatus(request.getStatus());

        employeeDataService.save(employeeEntity);
    }

    public void assignShopAndEmployee(Long shopId, Long employeeId) {

        Shop shopEntity = shopDataService.findById(shopId);
        Employee employeeEntity = employeeDataService.findById(employeeId);
        if(employeeEntity.getStatus().equals(EmployeeStatus.DELETE)) {
            throw new GongAssistanceException(GongAssistanceCode.E0006);
        }

        if(shopEntity.getStatus().equals(ShopStatus.DELETE)) {
            throw new GongAssistanceException(GongAssistanceCode.E0007);
        }

        if(employeeEntity.getShop() != null) {
            throw new GongAssistanceException(GongAssistanceCode.E0003);
        }

        employeeEntity.setShop(shopEntity);
        employeeDataService.save(employeeEntity);

    }
}
