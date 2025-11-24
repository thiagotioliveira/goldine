package dev.thiagooliveira.goldine.infrastructure.web.exception;

public class OfferingNotFoundException extends WebException {
  public OfferingNotFoundException() {
    super("Offering not found");
  }
}
