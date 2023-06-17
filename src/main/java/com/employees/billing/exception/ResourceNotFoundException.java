package com.employees.billing.exception;

/**
 * Исключительная ситуация, возникающая при отсутствии запрашиваемого ресурса.
 *
 * @author Irina Ilina
 */
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ResourceNotFoundException(String msg) {
    super(msg);
  }
}