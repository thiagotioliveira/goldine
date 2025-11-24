package dev.thiagooliveira.goldine.domain.model;

public enum Language {
  PT("Portuguese"),
  EN("English");
  private final String label;

  Language(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
