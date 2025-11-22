package dev.thiagooliveira.goldine.domain.model;

import dev.thiagooliveira.goldine.domain.exception.DomainException;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Catalog {
  private UUID id;
  private String name;
  private Language language;
  private Set<Category> categories;

  private Catalog(UUID id, String name, Language language, List<Category> categories) {
    this.id = id;
    this.name = name;
    this.language = language;
    this.categories = categories != null ? new HashSet<>(categories) : new HashSet<>();
  }

  public static Catalog create(String name, Language language) {
    validate(name);
    return new Catalog(UUID.randomUUID(), name, language, new ArrayList<>());
  }

  public static Catalog load(UUID id, String name, Language language, List<Category> categories) {
    return new Catalog(id, name, language, categories);
  }

  public void update(String name, Language language) {
    validate(name);
    this.name = name;
    this.language = language;
  }

  void addCategory(Category category) {
    this.categories.add(category);
  }

  boolean removeCategory(UUID categoryId) {
    return categories.removeIf(category -> category.getId().equals(categoryId));
  }

  public Optional<Category> findCategory(UUID categoryId) {
    return categories.stream().filter(category -> category.getId().equals(categoryId)).findFirst();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Catalog catalog = (Catalog) o;
    return Objects.equals(name, catalog.name) && language == catalog.language;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, language);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Language getLanguage() {
    return language;
  }

  public Set<Category> getCategories() {
    return Collections.unmodifiableSet(categories);
  }

  private static void validate(String name) {
    validateFieldName(name);
  }

  private static void validateFieldName(String fieldName) {
    if (StringUtils.isBlank(fieldName)) throw new DomainException(fieldName + " cannot be null");
    if (fieldName.length() < 3)
      throw new DomainException(fieldName + " cannot be less than 3 characters");
  }
}
