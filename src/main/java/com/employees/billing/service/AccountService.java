package com.employees.billing.service;

import com.employees.billing.entity.Account;
import com.employees.billing.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис по работе с балльным счетом.
 *
 * @author Irina Ilina
 */
@Service
public class AccountService {

    /** Репозиторий для работы с балльным счетом. */
    @Autowired
    private AccountRepository accountRepository;

    /**
     * Создание балльного счета.
     *
     * @param account балльный счет
     */
    @Transactional
    public void createAccount(Account account) {
        accountRepository.saveAndFlush(account);
    }

    /**
     * Получение информации о балльном счете.
     *
     * @param id идентификатор
     * @return информация о балльном счета
     */
    @Transactional
    public Account getAccountInfo(Long id) {
        return accountRepository.findById(id).get();
    }

    /**
     * Удаление балльного счета.
     *
     * @param id идентификатор балльного счета
     */
    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    /**
     * Получение баланса на балльном счете.
     *
     * @param id идентификатор балльного счета
     * @return баланс
     */
    @Transactional
    public double getBalance(Long id) {
        return accountRepository.findBalanceByAccountId(id);
    }

    /**
     * Увеличение балльного счета.
     *
     * @param id идентификатор балльного счета
     * @param amount количество баллов
     */
    @Transactional
    public void depositAmount(Long id, double amount) {
        accountRepository.saveBalanceByAccountId(id, amount);
    }

    /**
     * Списание с балльного счета.
     *
     * @param id идентификатор балльного счета
     * @param amount количество баллов
     */
    @Transactional
    public void withdrawAmount(Long id, double amount) {
        accountRepository.withdrawAmountByAccountId(id, amount);
    }
}
