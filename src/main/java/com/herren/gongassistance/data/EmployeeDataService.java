package com.herren.gongassistance.data;

import antlr.build.ANTLR;
import com.herren.gongassistance.base.exception.GongAssistanceCode;
import com.herren.gongassistance.base.exception.GongAssistanceException;
import com.herren.gongassistance.employee.domain.entity.Employee;
import com.herren.gongassistance.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeDataService {
    private final EmployeeRepository repository;

    public Employee save(Employee entity) {
        return repository.save(entity);
    }

    public Employee findById(Long employeeId) {
        return repository.findById(employeeId).orElseThrow(() -> new GongAssistanceException(GongAssistanceCode.E0002));
    }

    public Optional<Employee> findByTel(String tel) {
        return repository.findByTel(tel);
    }
}
