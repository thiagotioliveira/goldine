package dev.thiagooliveira.goldine.application.exception;

public class BusinessNotFoundException extends ApplicationException {

  public BusinessNotFoundException() {
    super("Business not found");
  }
}
