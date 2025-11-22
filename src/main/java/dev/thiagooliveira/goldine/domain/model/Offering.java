package dev.thiagooliveira.goldine.domain.model;

import dev.thiagooliveira.goldine.domain.exception.DomainException;
import java.math.BigDecimal;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Offering {
  private UUID id;
  private Language language;
  private BigDecimal price;
  private String name;
  private String description;
  private List<String> images;

  private Offering(
      UUID id,
      Language language,
      BigDecimal price,
      String name,
      String description,
      List<String> images) {
    this.id = id;
    this.language = language;
    this.price = price;
    this.name = name;
    this.description = description;
    this.images = images != null ? new ArrayList<>(images) : new ArrayList<>();
  }

  public static Offering create(
      Language language, BigDecimal price, String name, String description, List<String> images) {
    validate(price, name, description, images);
    return new Offering(UUID.randomUUID(), language, price, name, description, images);
  }

  public static Offering load(
      UUID id,
      Language language,
      BigDecimal price,
      String name,
      String description,
      List<String> images) {
    return new Offering(id, language, price, name, description, images);
  }

  public void update(BigDecimal price, String name, String description) {
    validate(price, name, description, images);
    this.price = price;
    this.name = name;
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Offering offering = (Offering) o;
    return Objects.equals(id, offering.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  public UUID getId() {
    return id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public List<String> getImages() {
    return Collections.unmodifiableList(images);
  }

  public Language getLanguage() {
    return language;
  }

  private static void validate(
      BigDecimal price, String name, String description, List<String> images) {
    validatePrice(price);
    validateFieldName(name);
    validateFieldName(description);
    validateImages(images);
  }

  private static void validateFieldName(String fieldName) {
    if (StringUtils.isBlank(fieldName)) throw new DomainException(fieldName + " cannot be null");
    if (fieldName.length() < 3)
      throw new DomainException(fieldName + " cannot be less than 3 characters");
  }

  private static void validatePrice(BigDecimal price) {
    if (price == null) throw new DomainException("Price cannot be null");
    if (price.compareTo(BigDecimal.ZERO) < 0) throw new DomainException("Price cannot be negative");
  }

  private static void validateImages(List<String> images) {
    if (images == null || images.isEmpty()) {
      throw new DomainException("Offering must have at least one image");
    }
    images.forEach(
        url -> {
          if (StringUtils.isBlank(url)) {
            throw new DomainException("Offering image URL cannot be blank");
          }
        });
  }
}
