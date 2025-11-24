package dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.offering;

import dev.thiagooliveira.goldine.domain.model.Language;
import java.math.BigDecimal;
import java.util.UUID;

public class OfferingProjection {
  private final Language language;
  private final UUID catalogId;
  private final String catalogName;
  private final UUID categoryId;
  private final String categoryName;
  private final UUID id;
  private final String name;
  private final String description;
  private final BigDecimal price;

  public OfferingProjection(
      Language language,
      UUID catalogId,
      String catalogName,
      UUID categoryId,
      String categoryName,
      UUID id,
      String name,
      String description,
      BigDecimal price) {
    this.language = language;
    this.catalogId = catalogId;
    this.catalogName = catalogName;
    this.categoryId = categoryId;
    this.categoryName = categoryName;
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public UUID getCatalogId() {
    return catalogId;
  }

  public String getCatalogName() {
    return catalogName;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Language getLanguage() {
    return language;
  }

  public String getDescription() {
    return description;
  }
}
