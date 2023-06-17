package com.employees.billing.exception;

import java.util.Date;

/**
 * Класс для определения сообщения при возникновении исключительной ситуации.
 *
 * @author Irina Ilina
 */
public class ErrorMessage {

  /** Код статуса. */
  private int statusCode;

  /** Дата. */
  private Date timestamp;

  /** Сообщение. */
  private String message;

  /** Описание. */
  private String description;

  /**
   * Конструктор.
   *
   * @param statusCode код статуса
   * @param timestamp дата
   * @param message сообщение
   * @param description описание
   */
  public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
    this.statusCode = statusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.description = description;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  public String getDescription() {
    return description;
  }
}