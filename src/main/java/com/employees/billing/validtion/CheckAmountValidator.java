package com.employees.billing.validtion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс, определяющий регулярное выражение для определения валидности баллов, зачисляемых на счет
 * или списываемых со счета.
 *
 * @author Irina Ilina
 */
public class CheckAmountValidator implements ConstraintValidator<CheckAmount, String> {

    /**
     * Инициализация.
     *
     * @param constraintAnnotation аннотация
     */
    @Override
    public void initialize(CheckAmount constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * Валидирует данные.
     *
     * @param text данные
     * @param constraintValidatorContext контекст
     * @return признак валидности
     */
    @Override
    public boolean isValid(String text, ConstraintValidatorContext constraintValidatorContext) {
        String regexp = "^\\d+(?:[\\.,]\\d+)?$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
}