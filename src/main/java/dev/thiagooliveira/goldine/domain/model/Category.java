package dev.thiagooliveira.goldine.domain.model;

import dev.thiagooliveira.goldine.domain.exception.DomainException;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Category {
  private UUID id;
  private Language language;
  private String name;
  private List<Offering> offerings;

  private Category(UUID id, Language language, String name, List<Offering> offerings) {
    this.id = id;
    this.language = language;
    this.name = name;
    this.offerings = offerings != null ? new ArrayList<>(offerings) : new ArrayList<>();
  }

  public static Category create(Language language, String name) {
    validate(name);
    return new Category(UUID.randomUUID(), language, name, new ArrayList<>());
  }

  public static Category load(UUID id, Language language, String name, List<Offering> offerings) {
    return new Category(id, language, name, offerings);
  }

  public void update(String name) {
    validate(name);
    this.name = name;
  }

  void addOffering(Offering offering) {
    this.offerings.add(offering);
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

  public List<Offering> getOfferings() {
    return Collections.unmodifiableList(offerings);
  }

  boolean removeOffering(UUID offeringId) {
    return offerings.removeIf(offering -> offering.getId().equals(offeringId));
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return language == category.language && Objects.equals(name, category.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(language, name);
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
