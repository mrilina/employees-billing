package com.employees.billing.entity;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.Hibernate;

import java.io.Serializable;

/**
 * Базовая сущность.
 *
 * @param <T> тип обьекта для первичного ключа
 *
 * @author Irina Ilina
 */
@MappedSuperclass
public abstract class AbstractEntity<T> implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Получить уникальный идентификатор сущности (PK).
     *
     * @return уникальный идентификатор сущности (PK)
     */
    public abstract T getId();

    /**
     * Установить уникальный идентификатор сущности (PK).
     *
     * @param id уникальный идентификатор сущности (PK)
     */
    public abstract void setId(T id);

    @Override
    public int hashCode() {
        if (getId() == null) {
            return super.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + getId().hashCode();
        return result;
    }

    /**
     * <p>Сравнивает две сущности.
     *
     * <p>Учитываются run-time типы и id (getId); если один из идентификаторов равен null,
     * вызывается метод equalsData, по умолчанию возвращающий false.
     *
     * @param obj объект для сравнения
     *
     * @return true если данные совпалдают, false если нет
     */
    @Override
    @SuppressWarnings({"EqualsWhichDoesntCheckParameterClass", "unchecked"})
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null ||
                // looks into the target class of a proxy if necessary
                !Hibernate.getClass(this).equals(Hibernate.getClass(obj))) {
            return false;
        }

        T thisId = getId();
        if (thisId != null) {
            T thatId = (T) ((AbstractEntity) obj).getId();
            if (thatId != null) {
                return thisId.equals(thatId);
            }
        }

        return equalsData(obj);
    }

    /**
     * <p>Сравнивает бизнес-данные. Вызывается из equals (Template Method). Классы
     * потомки должны переопределять этот метод, если предполагается их
     * использование в Set до сохранения.
     *
     * @param obj объект для сравнения
     *
     * @return true если данные совпалдают, false если нет
     */
    protected boolean equalsData(Object obj) {
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + getId() + "]";
    }
}
