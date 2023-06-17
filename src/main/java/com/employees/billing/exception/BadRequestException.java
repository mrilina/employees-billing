package com.employees.billing.exception;

/**
 * Исключительная ситуация, возникающая при некорректных параметрах запроса.
 *
 * @author Irina Ilina
 */
public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BadRequestException(String msg) {
    super(msg);
  }
}