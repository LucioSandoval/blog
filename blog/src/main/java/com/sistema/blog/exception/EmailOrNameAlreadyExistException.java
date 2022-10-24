package com.sistema.blog.exception;

public class EmailOrNameAlreadyExistException extends Exception {

  private static final long serialVersionUID = 1L;

  public EmailOrNameAlreadyExistException(String message) {
    super(message);
  }
}
