package com.example.employee.service;

import com.example.employee.entity.Employee;
import com.example.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    // ✅ Create / Post Employee
    public Employee postEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // ✅ Get All Employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // ✅ Get Employees by Firebase User ID
    public List<Employee> getEmployeesByUserId(String userId) {
        return employeeRepository.findByUserId(userId);
    }

    // ✅ Delete Employee by ID
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    // ✅ Get Employee by ID
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    // ✅ Update Employee
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhone(updatedEmployee.getPhone());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());

        // userId same rehna chahiye, isliye dobara set karte hain (safety)
        existingEmployee.setUserId(updatedEmployee.getUserId());

        return employeeRepository.save(existingEmployee);
    }
}
