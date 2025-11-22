package dev.thiagooliveira.goldine.infrastructure.web.exception;

public class BusinessNotFoundException extends WebException {
  public BusinessNotFoundException() {
    super("Business not found");
  }
}
