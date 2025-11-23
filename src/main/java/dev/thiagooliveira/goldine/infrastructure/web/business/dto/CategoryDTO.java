package dev.thiagooliveira.goldine.infrastructure.web.business.dto;

import dev.thiagooliveira.goldine.domain.model.Category;
import java.util.List;
import java.util.UUID;

public class CategoryDTO {

  private final UUID id;
  private final String name;
  private final List<OfferingDTO> offerings;

  public CategoryDTO(Category category) {
    this.id = category.getId();
    this.name = category.getName();
    this.offerings = category.getOfferings().stream().map(OfferingDTO::new).toList();
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<OfferingDTO> getOfferings() {
    return offerings;
  }
}
