package dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.catalog;

import dev.thiagooliveira.goldine.domain.model.Language;
import java.util.UUID;

public class CatalogProjection {

  private final UUID id;
  private final String name;
  private final Language language;

  public CatalogProjection(UUID id, String name, Language language) {
    this.id = id;
    this.name = name;
    this.language = language;
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
}
