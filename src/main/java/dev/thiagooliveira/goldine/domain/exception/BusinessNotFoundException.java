package dev.thiagooliveira.goldine.domain.exception;

public class BusinessNotFoundException extends DomainException {

  public BusinessNotFoundException() {
    super("Business not found");
  }
}
