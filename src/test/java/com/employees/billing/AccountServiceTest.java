package com.employees.billing;

import com.employees.billing.entity.Account;
import com.employees.billing.entity.Employee;
import com.employees.billing.repository.AccountRepository;
import com.employees.billing.service.AccountService;
import com.employees.billing.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Тестирование сервиса по работе с балльным счетом.
 *
 * @author Irina Ilina
 */
@SpringBootTest
public class AccountServiceTest {

    /** Вспомогательный класс, необходимый для тестирования реализации оптимистической блокировки. */
    @Autowired
    private InventoryService inventoryService;

    /** Репозиторий для работы с балльным счетом. */
    @Autowired
    private AccountRepository accountRepository;

    /** Сервис по работе с балльным счетом. */
    @SpyBean
    private AccountService accountService;

    /** Баллы для начисления и списания. */
    private final List<Integer> amounts = Arrays.asList(10, 5);

    /** Тест работы без блокировки. */
    @Test
    void incrementAmount_withoutConcurrency() {
        Employee employee = new Employee("Сидорова Антонина");
        Account initialAccount = new Account(employee, 251.0);
        accountRepository.save(initialAccount);
        assertEquals(0, initialAccount.getVersion());

        for (final int amount : amounts) {
            inventoryService.depositAmount(initialAccount.getId(), amount);
        }
        final Account account = accountRepository.findById(initialAccount.getId())
                .orElseThrow(() -> new IllegalArgumentException("Не найдена запись о балльном счете"));

        assertAll(
                () -> assertEquals(2, account.getVersion()),
                () -> assertEquals(266,0, account.getBalance()),
                () -> verify(accountService, times(1)).depositAmount(initialAccount.getId(), 10)
        );
    }

    /** Тест оптимистической блокировки. */
    @Test
    void incrementAmount_withOptimisticLockingHandling() throws InterruptedException {
        Employee employee = new Employee("Нестеров Дмитрий");
        Account initialAccount = new Account(employee, 251.0);
        accountRepository.save(initialAccount);
        assertEquals(0, initialAccount.getVersion());

        final ExecutorService executor = Executors.newFixedThreadPool(amounts.size());

        for (final int amount : amounts) {
            executor.execute(() -> inventoryService.depositAmount(initialAccount.getId(), amount));
        }

        executor.shutdown();
        assertTrue(executor.awaitTermination(1, TimeUnit.MINUTES));

        final Account account = accountRepository.findById(initialAccount.getId())
                .orElseThrow(() -> new IllegalArgumentException("Не найдена запись о балльном счете"));

        assertAll(
                () -> assertEquals(2, account.getVersion()),
                () -> assertEquals(266, account.getBalance()),
                () -> verify(accountService, times(1)).depositAmount(initialAccount.getId(), 10)
        );
    }
}
