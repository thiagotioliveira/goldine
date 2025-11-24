package dev.thiagooliveira.goldine.infrastructure.persistence.command.business.category;

import dev.thiagooliveira.goldine.domain.model.Category;
import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.domain.model.Offering;
import dev.thiagooliveira.goldine.infrastructure.persistence.command.business.catalog.CatalogEntity;
import dev.thiagooliveira.goldine.infrastructure.persistence.command.business.offering.OfferingEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "category")
public class CategoryEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "language")
  private Language language;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "catalog_id")
  private CatalogEntity catalog;

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private List<OfferingEntity> offerings = new ArrayList<>();

  public CategoryEntity() {}

  public static CategoryEntity fromDomain(Category category) {
    var entity = new CategoryEntity();
    entity.id = category.getId();
    entity.name = category.getName();
    entity.language = category.getLanguage();

    if (category.getOfferings() != null) {
      entity.offerings =
          category.getOfferings().stream()
              .map(OfferingEntity::fromDomain)
              .collect(Collectors.toList());
      entity.offerings.forEach(offering -> offering.setCategory(entity));
    }

    return entity;
  }

  public Category toDomain() {
    List<Offering> offeringList =
        offerings != null
            ? offerings.stream().map(OfferingEntity::toDomain).toList()
            : new ArrayList<>();
    return Category.load(id, language, name, offeringList);
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

  public CatalogEntity getCatalog() {
    return catalog;
  }

  public void setCatalog(CatalogEntity catalog) {
    this.catalog = catalog;
  }

  public List<OfferingEntity> getOfferings() {
    return offerings;
  }

  public void setOfferings(List<OfferingEntity> offerings) {
    this.offerings = offerings;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }
}
