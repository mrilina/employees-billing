package com.employees.billing.controller;

import com.employees.billing.entity.Account;
import com.employees.billing.entity.Employee;
import com.employees.billing.entity.History;
import com.employees.billing.exception.BadRequestException;
import com.employees.billing.exception.ResourceNotFoundException;
import com.employees.billing.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Контроллер по работе с балльным счетом.
 *
 * @author Irina Ilina
 */
@RestController
@RequestMapping("/api")
public class AccountController {

    /** Сервис по работе с балльным счетом. */
    @Autowired
    private AccountService accountService;

    /** Контроллер по работе с историей ведения балльного счета. */
    @Autowired
    private HistoryController historyController;

    /**
     * Создкание балльного счета.
     *
     * @param employee сведения о служащем
     * @param balance баланс баллов
     */
    public void createAccount(Employee employee, double balance) {
        Account account = new Account(employee, balance);
        accountService.createAccount(account);
    }

    /**
     * Получение баланса с балльного счета.
     *
     * @param id идентификатор балльного счета
     * @return баланс баллов
     */
    @GetMapping("/accounts/{id}/balance")
    public double getBalance(@PathVariable Long id) {
        return accountService.getBalance(id);
    }

    /**
     * Начисление средств на балльный счет.
     *
     * @param id идентификатор
     * @param amount количество баллов
     */
    @PutMapping("/accounts/{id}/deposit/{amount}")
    public void depositAmount(@PathVariable Long id, @PathVariable double amount) {
        double initialBalance = getBalance(id);
        accountService.depositAmount(id, amount);
        History history = new History("Deposited", "Success", initialBalance, initialBalance + amount, new Date());
        Account account = accountService.getAccountInfo(id);
        if (account == null) {
            throw new ResourceNotFoundException("Балльный счет с идентификатором " + id + " не наден в базе данных");
        }
        history.setAccount(account);
        historyController.addHistory(history);
    }

    /**
     * Списание баллов со счета.
     *
     * @param id идентификатор
     * @param amount количесвто баллов
     */
    @PutMapping("/accounts/{id}/withdraw/{amount}")
    public void withdrawAmount(@PathVariable Long id, @PathVariable double amount) {
        double initialBalance = getBalance(id);
        double finalBalance = initialBalance - amount;
        if (finalBalance >=0) {
            accountService.withdrawAmount(id, amount);
            History history = new History("Withdrawn", "Success", initialBalance, finalBalance, new Date());

            Account account = accountService.getAccountInfo(id);
            if (account == null) {
                throw new ResourceNotFoundException("Балльный счет с идентификатором " + id + " не наден в базе данных");
            }
            history.setAccount(account);
            historyController.addHistory(history);
        } else {
            throw new BadRequestException("Недостаточно баллов для списания на счете с идентификатором " + id);
        }
    }

    /**
     * Получение информации о балльном счете.
     *
     * @param id идентификатор балльного счета
     * @return информация о балльном счете
     */
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountInfo(@PathVariable Long id) {
        Account account = accountService.getAccountInfo(id);
        if (account == null) {
            throw new ResourceNotFoundException("Балльный счет с идентификатором " + id + " не наден в базе данных");
        }
        return ResponseEntity.ok()
             //   .eTag(Long.toString(account.getVersion()))
                .body(account);
    }

    /**
     * Удаление балльного счета.
     *
     * @param id идентификатор балльного счета
     */
    public String deleteAccount(Long id) {
        Account account = accountService.getAccountInfo(id);
        if (account == null) {
            throw new ResourceNotFoundException("Балльный счет с идентификатором " + id + " не наден в базе данных");
        }
        accountService.deleteAccount(id);
        return "Балльный счет с идентификатором = " + id + " был удален";
    }
}
