package com.employees.billing;

import com.employees.billing.entity.Account;
import com.employees.billing.entity.Employee;
import com.employees.billing.repository.AccountRepository;
import com.employees.billing.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Тестирование методов репозитория для работы с балльным счетом.
 *
 * @author Irina Ilina
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    /** Репозиторий для работы с балльным счетом. */
    @Autowired
    private AccountRepository accountRepository;

    /** Репозиторий для работы с информацией о служащих. */
    @Autowired
    private EmployeeRepository employeeRepository;

    /** Сведения о служащем. */
    private Employee employee;

    /** Сведения о балльном счете. */
    private Account account;

    /** Создание тестового служащего и балльного счета. */
    @BeforeEach
    public void setup() {
        employee = new Employee();
        employee.setUserName("FIRST_EMPLOYEE");

        account = new Account(employee, 250.0);
    }

    /** Тестирование всех операций с балльным счетом. */
    @Test
    void save_should_insert_new_account() {
        this.employeeRepository.save(employee);
        Account persistedAccount = this.accountRepository.save(account);
        assertNotNull(persistedAccount);
        assertThat(persistedAccount).isNotNull();
        assertThat(persistedAccount.getId()).isGreaterThan(0);

        List<Account> accountList = accountRepository.findAll();
        assertThat(accountList).isNotNull();
        assertThat(accountList.size()).isEqualTo(1);

        Account accountDB = accountRepository.findById(account.getId()).get();
        assertThat(accountDB).isNotNull();

        accountDB.setBalance(560.0);
        Account updatedAccount = accountRepository.save(accountDB);
        assertThat(accountDB.getBalance()).isEqualTo(560.0);

        accountRepository.deleteById(updatedAccount.getId());
        Optional<Account> accountOptional = accountRepository.findById(updatedAccount.getId());
        assertThat(accountOptional).isEmpty();
    }
}
