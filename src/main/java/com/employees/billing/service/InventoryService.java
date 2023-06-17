package com.employees.billing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Вспомогательный класс, необходимый для тестирования реализации оптимистической блокировки.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class InventoryService {

    /** Сервис по работе с балльным счетом. */
    private final AccountService accountService;

    /**
     * Начисление баллов.
     *
     * @param accountId идентификатор балльного счета
     * @param amount количество баллов
     */
    @Transactional
    public void depositAmount(Long accountId, double amount) {
        try {
            accountService.depositAmount(accountId, amount);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.warn("Изменение баланса уже было произведено: {} в параллельной транзакции. Повторение операции...", accountId);
            accountService.depositAmount(accountId, amount);
        }
    }

    /**
     * Списание баллов.
     *
     * @param accountId идентификатор балльного счета
     * @param amount количество баллов
     */
    @Transactional
    public void withdrawAmount(Long accountId, double amount) {
        try {
            accountService.withdrawAmount(accountId, amount);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.warn("Изменение баланса уже было произведено: {} в параллельной транзакции. Повторение операции...", accountId);
            accountService.withdrawAmount(accountId, amount);
        }
    }
}
