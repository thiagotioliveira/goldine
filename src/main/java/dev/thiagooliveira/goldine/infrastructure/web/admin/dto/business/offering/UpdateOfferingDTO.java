package dev.thiagooliveira.goldine.infrastructure.web.admin.dto.business.offering;

import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.offering.OfferingProjection;
import java.math.BigDecimal;
import java.util.UUID;

public class UpdateOfferingDTO {
  private UUID originalCategoryId;
  private UUID categoryId;
  private UUID id;
  private String name;
  private String description;
  private BigDecimal price;

  public UpdateOfferingDTO() {}

  public UpdateOfferingDTO(
      UUID originalCategoryId,
      UUID categoryId,
      UUID id,
      String name,
      String description,
      BigDecimal price) {
    this.originalCategoryId = originalCategoryId;
    this.categoryId = categoryId;
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public UpdateOfferingDTO(OfferingProjection offering) {
    this.originalCategoryId = offering.getCategoryId();
    this.categoryId = offering.getCategoryId();
    this.id = offering.getId();
    this.name = offering.getName();
    this.description = offering.getDescription();
    this.price = offering.getPrice();
  }

  public UUID getOriginalCategoryId() {
    return originalCategoryId;
  }

  public void setOriginalCategoryId(UUID originalCategoryId) {
    this.originalCategoryId = originalCategoryId;
  }

  public UUID getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(UUID categoryId) {
    this.categoryId = categoryId;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
