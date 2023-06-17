package com.employees.billing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Описание служащего.
 *
 * @author Irina Ilina
 */
@Entity
@Table(name = "employees")
public class Employee extends AbstractEntity<Long> {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Идентификтор. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="emp_id")
    private Long id;

    /** Имя пользователя. */
    @Column(name="emp_username")
    private String userName;

    /** Конструктор. */
    public Employee() {
    }

    /**
     * Конструктор.
     *
     * @param userName имя пользователя
     */
    public Employee(String userName) {
        this.userName = userName;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
