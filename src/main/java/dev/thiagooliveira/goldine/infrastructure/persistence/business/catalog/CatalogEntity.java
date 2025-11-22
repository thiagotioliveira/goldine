package dev.thiagooliveira.goldine.infrastructure.persistence.business.catalog;

import dev.thiagooliveira.goldine.domain.model.Catalog;
import dev.thiagooliveira.goldine.domain.model.Category;
import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.infrastructure.persistence.business.BusinessEntity;
import dev.thiagooliveira.goldine.infrastructure.persistence.business.category.CategoryEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "catalog")
public class CatalogEntity {

  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "business_id")
  private BusinessEntity business;

  @Enumerated(EnumType.STRING)
  @Column(name = "language")
  private Language language;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CategoryEntity> categories = new ArrayList<>();

  public CatalogEntity() {}

  public static CatalogEntity fromDomain(Catalog catalog) {
    var entity = new CatalogEntity();
    entity.id = catalog.getId();
    entity.name = catalog.getName();
    entity.language = catalog.getLanguage();
    if (catalog.getCategories() != null) {
      entity.setCategories(
          catalog.getCategories().stream().map(CategoryEntity::fromDomain).toList());
      entity.getCategories().forEach(category -> category.setCatalog(entity));
    }
    return entity;
  }

  public Catalog toDomain() {
    List<Category> categoryList = categories.stream().map(CategoryEntity::toDomain).toList();
    return Catalog.load(id, name, language, categoryList);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    CatalogEntity that = (CatalogEntity) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public BusinessEntity getBusiness() {
    return business;
  }

  public void setBusiness(BusinessEntity business) {
    this.business = business;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<CategoryEntity> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryEntity> categories) {
    this.categories = categories;
  }
}
