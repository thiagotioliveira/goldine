package dev.thiagooliveira.goldine.domain.exception;

public class CategoryNotFoundException extends DomainException {

  public CategoryNotFoundException() {
    super("Category not found");
  }
}
