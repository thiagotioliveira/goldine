package dev.thiagooliveira.goldine.application.exception;

public class CategoryNotFoundException extends ApplicationException {
  public CategoryNotFoundException() {
    super("Catalog not found");
  }
}
