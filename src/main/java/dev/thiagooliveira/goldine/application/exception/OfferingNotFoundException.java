package dev.thiagooliveira.goldine.application.exception;

public class OfferingNotFoundException extends ApplicationException {
  public OfferingNotFoundException() {
    super("Offering not found");
  }
}
