package dev.thiagooliveira.goldine.infrastructure.web.exception;

public class CatalogNotFoundException extends WebException {
  public CatalogNotFoundException() {
    super("Catalog not found");
  }
}
