package com.employees.billing.controller;

import com.employees.billing.entity.Employee;
import com.employees.billing.exception.ResourceNotFoundException;
import com.employees.billing.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для работы со сведениями о служащем.
 *
 * @author Irina Ilina
 */
@RestController
@RequestMapping("/api")
public class EmployeeController {

    /** Сервис по работе со служащим. */
    @Autowired
    private EmployeeService employeeService;

    /** Контроллер по работе с балльным счетом. */
    @Autowired
    private AccountController accountController;

    /**
     * Создание служащего.
     *
     * @param employee сведения о служащем
     */
    @PostMapping("/employees")
    public void createEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
        accountController.createAccount(employee, 0);
    }

    /**
     * Получение информации о служащем.
     *
     * @param id идентификатор служащего
     * @return информация о служащем
     */
    @GetMapping("/employees/{id}")
    public Employee getEmployeeInfo(@PathVariable Long id) {
        Employee employee =  employeeService.getEmployeeInfo(id);
        if (employee == null) {
            throw new ResourceNotFoundException("Служащий с идентификатором " + id + " не наден в базе данных");
        }
        return employee;
    }

    /**
     * Удаление информации о служащем.
     *
     * @param id идентификатор служащего
     */
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeInfo(id);
        if (employee == null) {
            throw new ResourceNotFoundException("Служащий с идентификатором " + id + " не наден в базе данных");
        }
        employeeService.deleteEmployee(id);
        return "Служащий с идентификатором = " + id + " был удален";
    }

}
