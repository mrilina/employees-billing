package com.employees.billing.validtion;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для проверки количества начисляемых/списываемых баллов.
 *
 * @author Irina Ilina
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckAmountValidator.class)
public @interface CheckAmount {

    /** Значение. */
    public String value() default "0.0";

    /** Сообщение. */
    public String message() default "необходимо определить значение типа число с плавающей запятой";

    /** Область действия. */
    public Class<?>[] group() default {};

    /** Данные. */
    public Class<? extends Payload> [] payload() default {} ;
}
