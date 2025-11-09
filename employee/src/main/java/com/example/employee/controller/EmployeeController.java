package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = {
        "https://employee-frontend-rj1xf6o8r-princes-projects-27e0de7f.vercel.app/",

        "https://employee-frontend-mgc6ddvax-princes-projects-27e0de7f.vercel.app",
        "http://localhost:3000"
}) // ✅ CORS fix deployed on Render
public class EmployeeController {

    private final EmployeeService employeeService;

    // ✅ Get all employees (for testing or admin)
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // ✅ Post new employee
    @PostMapping("/employee")
    public Employee postEmployee(@RequestBody Employee employee) {
        return employeeService.postEmployee(employee);
    }

    // ✅ Get employees by Firebase userId (new endpoint)
    @GetMapping("/employee/user/{userId}")
    public ResponseEntity<List<Employee>> getEmployeesByUser(@PathVariable String userId) {
        try {
            List<Employee> employees = employeeService.getEmployeesByUserId(userId);
            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ Delete by id
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Deleted employee with ID " + id + " successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Get employee by ID (numeric)
    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ✅ Update employee by ID
    @PatchMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(employee);
    }

    // ✅ Health check (Render testing)
    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}
