package dev.thiagooliveira.goldine.application.exception;

public class CatalogNotFoundException extends ApplicationException {

  public CatalogNotFoundException() {
    super("Catalog not found");
  }
}
