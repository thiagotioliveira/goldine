package dev.thiagooliveira.goldine.infrastructure.persistence.business.offering;

import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.domain.model.Offering;
import dev.thiagooliveira.goldine.infrastructure.persistence.business.category.CategoryEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "offering")
public class OfferingEntity {

  @Id private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private CategoryEntity category;

  @Enumerated(EnumType.STRING)
  @Column(name = "language")
  private Language language;

  private BigDecimal price;

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @OneToMany(mappedBy = "offering", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OfferingImageEntity> images = new ArrayList<>();

  public OfferingEntity() {}

  public static OfferingEntity fromDomain(Offering offering) {
    var entity = new OfferingEntity();
    entity.id = offering.getId();
    entity.name = offering.getName();
    entity.description = offering.getDescription();
    entity.price = offering.getPrice();
    entity.language = offering.getLanguage();
    offering
        .getImages()
        .forEach(
            imageUrl -> {
              OfferingImageEntity imageEntity = new OfferingImageEntity(entity, imageUrl);
              entity.getImages().add(imageEntity);
            });
    return entity;
  }

  public Offering toDomain() {
    return Offering.load(
        id,
        language,
        price,
        name,
        description,
        this.images.stream().map(OfferingImageEntity::getName).collect(Collectors.toList()));
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
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

  public List<OfferingImageEntity> getImages() {
    return images;
  }

  public void setImages(List<OfferingImageEntity> images) {
    this.images = images;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }
}
