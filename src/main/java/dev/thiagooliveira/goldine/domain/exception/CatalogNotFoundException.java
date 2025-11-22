package dev.thiagooliveira.goldine.domain.exception;

public class CatalogNotFoundException extends DomainException {

  public CatalogNotFoundException() {
    super("Catalog not found");
  }
}
