package com.employees.billing.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * История проведения операций.
 *
 * @author Irina Ilina
 */
@Entity
@Table(name = "history")
public class History  extends AbstractEntity<Long>{

    /** Идентификатор. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hst_id")
    private Long id;

    /** Тип транзакции. */
    @Column(name="hst_type")
    private String transactionType;

    /** Статус транзакции. */
    @Column(name="hst_status")
    private String transactionStatus;

    /** Начальное количество баллов. */
    @Column(name="hst_initial_amount")
    private double initialBalance;

    /** Конечное количество баллов. */
    @Column(name="hst_final_amount")
    private double finalBalance;

    /** Дата проведения операции. */
    @Temporal(TemporalType.DATE)
    @Column(name = "hst_date")
    private Date createDate;

    /** Балльный счет. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hst_acc_id")
    private Account account;

    /** Конструктор. */
    public History() {
    }

    /**
     * Конструктор.
     *
     * @param transactionType тип транзакции
     * @param transactionStatus статус
     * @param initialBalance начальный баланс
     * @param finalBalance конечный баланс
     * @param createDate дата проведения операции
     */
    public History(String transactionType, String transactionStatus, double initialBalance, double finalBalance, Date createDate) {
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
        this.initialBalance = initialBalance;
        this.finalBalance = finalBalance;
        this.createDate = createDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }

    public double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "History{" +
                "transactionType='" + transactionType + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                ", initialBalance=" + initialBalance +
                ", finalBalance=" + finalBalance +
                ", createDate=" + createDate +
                '}';
    }
}
