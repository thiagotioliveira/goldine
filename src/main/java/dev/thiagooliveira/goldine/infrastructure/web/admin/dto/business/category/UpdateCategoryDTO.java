package dev.thiagooliveira.goldine.infrastructure.web.admin.dto.business.category;

import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.category.CategoryProjection;
import java.util.UUID;

public class UpdateCategoryDTO {
  private UUID originalCatalogId;
  private UUID catalogId;
  private UUID id;
  private String name;

  public UpdateCategoryDTO() {}

  public UpdateCategoryDTO(UUID catalogId, UUID id, String name) {
    this.catalogId = catalogId;
    this.originalCatalogId = catalogId;
    this.id = id;
    this.name = name;
  }

  public UpdateCategoryDTO(CategoryProjection categoryProjection) {
    this.id = categoryProjection.getId();
    this.name = categoryProjection.getName();
    this.originalCatalogId = categoryProjection.getCatalogId();
    this.catalogId = categoryProjection.getCatalogId();
  }

  public UUID getCatalogId() {
    return catalogId;
  }

  public void setCatalogId(UUID catalogId) {
    this.catalogId = catalogId;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getOriginalCatalogId() {
    return originalCatalogId;
  }

  public void setOriginalCatalogId(UUID originalCatalogId) {
    this.originalCatalogId = originalCatalogId;
  }
}
