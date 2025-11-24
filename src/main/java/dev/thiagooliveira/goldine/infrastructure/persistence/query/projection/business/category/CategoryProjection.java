package dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.category;

import dev.thiagooliveira.goldine.domain.model.Language;
import java.util.UUID;

public class CategoryProjection {

  private final Language language;
  private final UUID catalogId;
  private final String catalogName;
  private final UUID id;
  private final String name;

  public CategoryProjection(
      Language language, UUID catalogId, String catalogName, UUID id, String name) {
    this.language = language;
    this.catalogId = catalogId;
    this.catalogName = catalogName;
    this.id = id;
    this.name = name;
  }

  public Language getLanguage() {
    return language;
  }

  public UUID getCatalogId() {
    return catalogId;
  }

  public String getCatalogName() {
    return catalogName;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
