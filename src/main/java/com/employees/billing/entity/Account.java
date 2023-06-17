package com.employees.billing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Описание балльного счета.
 *
 * @author Irina Ilina
 */
@Entity
@Table(name = "accounts")
public class Account extends AbstractLockingEntity {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Идентификатор. */
    @Id
    @Column(name="acc_id")
    private Long id;

    /** Сведения о служащем. */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    @JoinColumn(name = "acc_id")
    private Employee employee;

    /** Количество баллов. */
    @Column(name="acc_balance")
    private Double balance;

    /** Конструктор. */
    public Account() {
    }

    /**
     * Конструктор.
     *
     * @param employee сведения о служащем
     * @param balance количество баллов
     */
    public Account(Employee employee, Double balance) {
        this.employee = employee;
        this.balance = balance;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
