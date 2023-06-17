package com.employees.billing;

import com.employees.billing.entity.Account;
import com.employees.billing.entity.Employee;
import com.employees.billing.entity.History;
import com.employees.billing.repository.AccountRepository;
import com.employees.billing.repository.EmployeeRepository;
import com.employees.billing.repository.HistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тестирование методов репозитория для работы с историей.
 *
 * @author Irina Ilina
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class HistoryRepositoryTest {

    /** Репозиторий для работы с балльным счетом. */
    @Autowired
    private AccountRepository accountRepository;

    /** Репозиторий для работы с информацией о служащих. */
    @Autowired
    private EmployeeRepository employeeRepository;

    /** Репозиторий для работы с историей. */
    @Autowired
    private HistoryRepository historyRepository;

    /** Сведения о служащем. */
    private Employee employee;

    /** Сведения о балльном счете. */
    private Account account;

    /** Сведения об истории. */
    private History history;

    /** Создание тестового служащего и балльного счета. */
    @BeforeEach
    public void setup() {
        employee = new Employee();
        employee.setUserName("FIRST_EMPLOYEE");

        account = new Account(employee, 250.0);

        history = new History("Deposited", "Success", 100, 200, new Date());
        history.setAccount(account);
    }


    /** Тестирование создания нового служащего. */
    @Test
    void save_should_insert_new_employee() {
        this.employeeRepository.save(employee);
        this.accountRepository.save(account);
        History persistedHistory = this.historyRepository.save(history);
        assertNotNull(persistedHistory);
        assertThat(persistedHistory).isNotNull();
        assertThat(persistedHistory.getId()).isGreaterThan(0);

        History history1 = new History("Deposited", "Success", 200, 250, new Date());
        history1.setAccount(account);
        this.historyRepository.save(history1);

        List<History> historyList = historyRepository.findAll();

        assertThat(historyList).isNotNull();
        assertThat(historyList.size()).isEqualTo(2);

        History historyDB = historyRepository.findById(history1.getId()).get();
        assertThat(historyDB).isNotNull();

        historyDB.setTransactionType("Check");
        History updatedHistory =  historyRepository.save(historyDB);
        assertThat(updatedHistory.getTransactionType()).isEqualTo("Check");


        historyRepository.deleteById(historyDB.getId());
        Optional<History> historyOptional = historyRepository.findById(historyDB.getId());
        assertThat(historyOptional).isEmpty();
    }

}
