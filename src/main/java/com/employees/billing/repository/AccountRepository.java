package com.employees.billing.repository;

import com.employees.billing.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с балльным счетом.
 *
 * @author Irina Ilina
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long>, JpaRepository<Account, Long> {

    /**
     * Получение баланса баллов на счете.
     *
     * @param id идентификатор балльного счета
     * @return баланс баллов
     */
    @Query("select balance from Account where id = ?1")
    public double findBalanceByAccountId(long id);

    /**
     * Увеличение баланса балльного счета.
     *
     * @param id идентификатор балльного счета
     * @param balance баланс баллов
     */
    @Modifying(clearAutomatically = true)
    //@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("update Account set balance = balance + ?2, version = version + 1 where id = ?1")
    public void saveBalanceByAccountId(long id, double balance);

    /**
     * Уменьшение баланса балльного счета.
     *
     * @param id идентификатор балльного счета
     * @param balance баланс баллов
     */
    @Modifying(clearAutomatically = true)
    //@Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @Query("update Account set balance = balance - ?2, version = version + 1 where id = ?1")
    public void withdrawAmountByAccountId(long id, double balance);
}
