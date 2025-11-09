package com.example.employee.controller;

import com.example.employee.entity.Employee;

import com.example.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "https://employee-frontend-flame.vercel.app",
        "https://employee-frontend-cemm9z9s0-princes-projects-27e0de7f.vercel.app",
        "https://employee-frontend-mgc6ddvax-princes-projects-27e0de7f.vercel.app/",
        "http://localhost:3000"
}) // ✅ CORS fix deployed on Render
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")  // plural recommended
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @PostMapping("/employee")
    public Employee postEmployee(@RequestBody Employee  employee)
    {
        return employeeService.postEmployee(employee);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return  new ResponseEntity<>("delete with Id "  + id+" delete successfully", HttpStatus.OK);
        } catch (Exception e) {
            return  new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping ("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        if ( employee == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(employee);

    }


    @GetMapping("/health")
    public String health() {
        return "OK";
    }


}
