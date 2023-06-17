package com.employees.billing.service;

import com.employees.billing.entity.Employee;
import com.employees.billing.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис по работе со служащим.
 *
 * @author Irina Ilina
 */
@Service
public class EmployeeService {

    /** Репозиторий для работы со служащим. */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Создание служащего.
     *
     * @param employee сведения о служащем
     */
    @Transactional
    public void createEmployee(Employee employee) {
        employeeRepository.saveAndFlush(employee);
    }

    /**
     * Получение сведений о служащем.
     *
     * @param id идентификатор служащего
     * @return сведения о служащем
     */
    @Transactional
    public Employee getEmployeeInfo(long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    /**
     * Удаление сведений о служащем.
     *
     * @param id идентификатор служащего
     */
    @Transactional
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
