package com.herren.gongassistance.employee.service;

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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmployeeServiceTest {

    @Mock
    private EmployeeDataService employeeDataService;

    @Mock
    private ShopDataService shopDataService;

    @Mock
    private RedisLockService redisLockService;

    @InjectMocks
    private EmployeeService sut;

    @Captor
    private ArgumentCaptor<Runnable> captor;

    @Test
    @DisplayName("employee save 정상 케이스")
    void addEmployee() {
        EmployeeAddRequest request = EmployeeAddRequest.builder()
                .name("test")
                .tel("01012345678")
                .build();

        Employee employee = Employee.builder()
                .name("test")
                .tel("01012345678")
                .status(EmployeeStatus.NORMAL)
                .build();

        sut.addEmployee(request);

        Mockito.verify(redisLockService).runWithLock(any(), any(), captor.capture());
        captor.getValue().run();
        Mockito.verify(employeeDataService).save(employee);
    }

    @Test
    @DisplayName("Employee 상태 Update 할때 존재하지 않는 식별자 호출 케이스")
    void unhappyUpdateEmployeeStatus() {

        given(employeeDataService.findById(any())).willThrow(GongAssistanceException.class);

        Assertions.assertThrows(GongAssistanceException.class, () -> {
            sut.updateEmployeeStatus(1L, EmployeeUpdateRequest.builder().status(EmployeeStatus.NORMAL).build());
        });
    }

    @Test
    @DisplayName("Employee 상태 Update 정상 케이스")
    void happyUpdateEmployeeStatus() {

        given(employeeDataService.findById(any())).willReturn(Employee.builder()
                .id(1L)
                .name("test")
                .tel("01011112222")
                .status(EmployeeStatus.NORMAL)
                .kakaotalkId(null)
                .build());
        EmployeeUpdateRequest request = EmployeeUpdateRequest.builder().status(EmployeeStatus.DELETE).build();

        sut.updateEmployeeStatus(1L, request);

        Mockito.verify(employeeDataService).findById(1L);

        Employee employee = Employee.builder()
                .id(1L)
                .name(null)
                .tel(null)
                .status(EmployeeStatus.DELETE)
                .kakaotalkId(null)
                .shop(null)
                .build();

        Mockito.verify(employeeDataService).save(employee);
    }

    @Test
    @DisplayName("샵에 직원 등록 정상 케이스")
    void happyAssignShopAndEmployee() {
        given(employeeDataService.findById(any())).willReturn(Employee.builder()
                .id(1L)
                .name("test")
                .tel("01011112222")
                .status(EmployeeStatus.NORMAL)
                .kakaotalkId(null)
                .build());

        given(shopDataService.findById(any())).willReturn(Shop.builder()
                .id(1L)
                .status(ShopStatus.READY)
                .tel("01011112222")
                .shopName("test")
                .kakaotalkId("test@test.com")
                .bizNo("1234567890")
                .build());

        sut.assignShopAndEmployee(1L, 1L);

        Mockito.verify(shopDataService).findById(1L);
        Mockito.verify(employeeDataService).findById(1L);

        Employee employee = Employee.builder()
                .id(1L)
                .name("test")
                .tel("01011112222")
                .status(EmployeeStatus.NORMAL)
                .kakaotalkId(null)
                .build();

        Shop shop = Shop.builder()
                .id(1L)
                .status(ShopStatus.READY)
                .tel("01011112222")
                .shopName("test")
                .kakaotalkId("test@test.com")
                .bizNo("1234567890")
                .build();

        employee.setShop(shop);
        Mockito.verify(employeeDataService).save(employee);
    }

    @Test
    @DisplayName("샵에 직원 등록시 샵 또는 직원이 존재하지 않는 비정상 케이스")
    void unhappyAssignShopAndEmployee() {
        given(employeeDataService.findById(any())).willThrow(GongAssistanceException.class);

        given(shopDataService.findById(any())).willThrow(GongAssistanceException.class);

        Assertions.assertThrows(GongAssistanceException.class, () -> {
            sut.assignShopAndEmployee(2L, 2L);
        });
    }

    @Test
    @DisplayName("직원이 특정 샵에 이미 등록되어 있는 비정상 케이스")
    void alreadyAssignShopAndEmployee() {
        given(employeeDataService.findById(1L)).willReturn(Employee.builder()
                .id(1L)
                .name("test")
                .tel("01011112222")
                .status(EmployeeStatus.NORMAL)
                .kakaotalkId(null)
                .shop(Shop.builder()
                        .id(1L)
                        .status(ShopStatus.READY)
                        .tel("01011112222")
                        .shopName("test")
                        .kakaotalkId("test@test.com")
                        .bizNo("1234567890")
                        .build())
                .build());

        given(shopDataService.findById(1L)).willReturn(Shop.builder()
                .id(1L)
                .status(ShopStatus.READY)
                .tel("01011112222")
                .shopName("test")
                .kakaotalkId("test@test.com")
                .bizNo("1234567890")
                .build());

        given(shopDataService.findById(2L)).willReturn(Shop.builder()
                .id(2L)
                .status(ShopStatus.READY)
                .tel("01011112222")
                .shopName("test")
                .kakaotalkId(null)
                .bizNo("1134567890")
                .build());


        Assertions.assertThrows(GongAssistanceException.class, () -> {
            sut.assignShopAndEmployee(2L, 1L);
        });
    }

}