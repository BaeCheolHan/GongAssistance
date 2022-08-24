package com.herren.gongassistance.employee.controller;

import com.herren.gongassistance.base.response.BaseResponse;
import com.herren.gongassistance.base.exception.GongAssistanceCode;
import com.herren.gongassistance.employee.domain.dto.EmployeeAddRequest;
import com.herren.gongassistance.employee.domain.dto.EmployeeUpdateRequest;
import com.herren.gongassistance.employee.service.EmployeeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Api(value = "Shop API")
@RequestMapping(path = "employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public BaseResponse addEmployee(@Validated @RequestBody EmployeeAddRequest employeeAddRequest) {
        employeeService.addEmployee(employeeAddRequest);
        return BaseResponse.builder().code(GongAssistanceCode.SUCCESS).message(GongAssistanceCode.SUCCESS.getMessage()).build();
    }

    @PutMapping(path = "{employeeId}")
    public BaseResponse updateEmployeeStatus(@Validated @RequestBody EmployeeUpdateRequest request, @PathVariable(name = "employeeId") Long employeeId) {
        employeeService.updateEmployeeStatus(employeeId, request);
        return BaseResponse.builder().code(GongAssistanceCode.SUCCESS).message(GongAssistanceCode.SUCCESS.getMessage()).build();
    }

    @PutMapping(path = "assign/{employeeId}")
    public BaseResponse assignShopAndEmployee(@PathVariable(name="employeeId") Long employeeId, @RequestBody Long shopId) {
        employeeService.assignShopAndEmployee(shopId, employeeId);
        return BaseResponse.builder().code(GongAssistanceCode.SUCCESS).message(GongAssistanceCode.SUCCESS.getMessage()).build();
    }

}
