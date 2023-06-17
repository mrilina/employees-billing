package com.employees.billing.repository;

import com.employees.billing.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с информацией о служащих.
 *
 * @author Irina Ilina
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
