package com.employees.billing.entity;

import java.io.Serializable;

/**
 * Указывает на то, что сущность БД должна реализовывать стратегию
 * оптимистичного блокирования (optimistic locking).
 *
 * <p><b>Важно: все поля интерфейса заполняются автоматически.</b>
 *
 * @author Irina Ilina
 */
public interface OptimisticLockingSupport extends Serializable {

    /**
     * <p>Установить значение оптимистичной блокировки.
     *
     * <p>Поле для реализацие стратегии оптимистичного блокирования (optimistic locking).
     *
     * @return значение оптимистичного блокирования
     */
    long getVersion();

    /**
     * <p>Установить значение оптимистичной блокировки.
     *
     * <p>Поле для реализацие стратегии оптимистичного блокирования (optimistic locking).
     *
     * <p><b>Важно: это поле заполняется автоматически.</b>
     *
     * @param version оптимистичного блокирования
     */
    void setVersion(long version);
}
