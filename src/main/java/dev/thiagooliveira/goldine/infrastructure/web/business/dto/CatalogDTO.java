package dev.thiagooliveira.goldine.infrastructure.web.business.dto;

import dev.thiagooliveira.goldine.domain.model.Catalog;
import java.util.List;
import java.util.UUID;

public class CatalogDTO {

  private final UUID id;
  private final String name;
  private final String language;
  private final List<CategoryDTO> categories;

  public CatalogDTO(Catalog catalog) {
    this.id = catalog.getId();
    this.name = catalog.getName();
    this.language = catalog.getLanguage().name();
    this.categories = catalog.getCategories().stream().map(CategoryDTO::new).toList();
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getLanguage() {
    return language;
  }

  public List<CategoryDTO> getCategories() {
    return categories;
  }
}
