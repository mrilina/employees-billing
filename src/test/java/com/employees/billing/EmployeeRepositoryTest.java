package com.employees.billing;

import com.employees.billing.entity.Employee;
import com.employees.billing.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тестирование методов репозитория для работы со служащим.
 *
 * @author Irina Ilina
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    /** Репозиторий для работы с информацией о служащих. */
    @Autowired
    private EmployeeRepository employeeRepository;

    /** Сведения о служащем. */
    private Employee employee;

    /** Создание тестового служащего. */
    @BeforeEach
    public void setup() {
        employee = new Employee();
        employee.setUserName("FIRST_EMPLOYEE");
    }

    /** Тестирование создания нового служащего. */
    @Test
    void save_should_insert_new_employee() {
        Employee persistedEmployee = this.employeeRepository.save(employee);
        assertNotNull(persistedEmployee);
        assertThat(persistedEmployee).isNotNull();
        assertThat(persistedEmployee.getId()).isGreaterThan(0);
    }

    /** Тестирование получения списка всех служащих. */
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList(){
        Employee employee1 = new Employee();
        employee.setUserName("SECOND_EMPLOYEE");

        employeeRepository.save(employee);
        employeeRepository.save(employee1);
        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    /** Тестирование сохранение информации о служащем и получения ее из базы. */
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        employeeRepository.save(employee);
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        assertThat(employeeDB).isNotNull();
    }

    /** Тестирование обновления информации о служащем.*/
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        employeeRepository.save(employee);
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();

        savedEmployee.setUserName("REPLACED_NAME");
        Employee updatedEmployee =  employeeRepository.save(savedEmployee);
        assertThat(updatedEmployee.getUserName()).isEqualTo("REPLACED_NAME");
    }

    /** Тестирование удаления сотрудника. */
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        assertThat(employeeOptional).isEmpty();
    }
}
