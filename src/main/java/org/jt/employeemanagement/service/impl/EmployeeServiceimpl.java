package org.jt.employeemanagement.service.impl;

import java.util.List;
import java.util.Optional;

import org.jt.employeemanagement.model.Employee;
import org.jt.employeemanagement.repository.EmployeeRepository;
import org.jt.employeemanagement.service.EmployeeService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceimpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);

    }

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void removeEmployeeById(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Optional<Employee> employeeById(String id) {
        return employeeRepository.findById(id);
    }
}
