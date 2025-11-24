package dev.thiagooliveira.goldine.infrastructure.web.admin.dto.business.catalog;

import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.catalog.CatalogProjection;
import java.util.UUID;

public class UpdateCatalogDTO {

  private UUID id;
  private UUID businessId;
  private String name;
  private String language = "EN";

  public UpdateCatalogDTO() {}

  public UpdateCatalogDTO(UUID businessId) {
    this.businessId = businessId;
  }

  public UpdateCatalogDTO(UUID businessId, CatalogProjection catalog) {
    this.id = catalog.getId();
    this.businessId = businessId;
    this.name = catalog.getName();
    this.language = catalog.getLanguage().name();
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getBusinessId() {
    return businessId;
  }

  public void setBusinessId(UUID businessId) {
    this.businessId = businessId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }
}
