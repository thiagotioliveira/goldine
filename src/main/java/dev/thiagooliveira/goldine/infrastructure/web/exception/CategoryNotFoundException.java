package dev.thiagooliveira.goldine.infrastructure.web.exception;

public class CategoryNotFoundException extends WebException {
  public CategoryNotFoundException() {
    super("Category not found");
  }
}
