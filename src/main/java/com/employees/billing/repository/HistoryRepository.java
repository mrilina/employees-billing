package com.employees.billing.repository;

import com.employees.billing.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с историей.
 *
 * @author Irina Ilina
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

}
