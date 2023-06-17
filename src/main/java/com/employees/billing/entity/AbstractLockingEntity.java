package com.employees.billing.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

/**
 * Базовая сущность с поддержкой оптимистической блокировкой и ключом целочисленного типа.
 *
 * @author Irina Ilina
 */
@MappedSuperclass
public abstract class AbstractLockingEntity extends AbstractEntity<Long> implements OptimisticLockingSupport {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Поле для реализации стратегии оптимистической блокировки. */
    @Version
    private long version;

    @Override
    public long getVersion() {
        return version;
    }

    @Override
    public void setVersion(long version) {
        this.version = version;
    }

}
