package dev.thiagooliveira.goldine.infrastructure.persistence.business;

import dev.thiagooliveira.goldine.domain.model.Business;
import dev.thiagooliveira.goldine.domain.model.Catalog;
import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.domain.model.SocialLink;
import dev.thiagooliveira.goldine.infrastructure.persistence.business.catalog.CatalogEntity;
import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "business")
public class BusinessEntity {

  @Id private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true, name = "business_alias")
  private String alias;

  @Column(nullable = true)
  private String address;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "business_languages", joinColumns = @JoinColumn(name = "business_id"))
  @Column(name = "language")
  @Enumerated(EnumType.STRING)
  private Set<Language> supportedLanguages;

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SocialLinkEntity> socialLinks;

  @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CatalogEntity> catalogs;

  public static BusinessEntity fromDomain(Business business) {
    var entity = new BusinessEntity();
    entity.id = business.getId();
    entity.name = business.getName();
    entity.alias = business.getAlias();
    entity.address = business.getAddress().orElse(null);
    entity.supportedLanguages = business.getSupportedLanguages();
    entity.socialLinks =
        business.getSocialLinks().stream()
            .map(SocialLinkEntity::fromDomain)
            .collect(Collectors.toList());
    entity.socialLinks.forEach(link -> link.setBusiness(entity));
    entity.catalogs =
        business.getCatalogs().stream().map(CatalogEntity::fromDomain).collect(Collectors.toList());

    entity.catalogs.forEach(catalog -> catalog.setBusiness(entity));
    return entity;
  }

  public Business toDomain() {
    Set<Language> languages =
        supportedLanguages != null ? new HashSet<>(supportedLanguages) : new HashSet<>();
    Set<Catalog> catalogs =
        this.catalogs != null
            ? this.catalogs.stream().map(CatalogEntity::toDomain).collect(Collectors.toSet())
            : new HashSet<>();
    Set<SocialLink> links =
        socialLinks != null
            ? socialLinks.stream().map(SocialLinkEntity::toDomain).collect(Collectors.toSet())
            : new HashSet<>();
    return Business.load(
        this.id,
        this.name,
        this.alias,
        Optional.ofNullable(this.address),
        links,
        languages,
        catalogs);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BusinessEntity that = (BusinessEntity) o;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Set<Language> getSupportedLanguages() {
    return supportedLanguages;
  }

  public void setSupportedLanguages(Set<Language> supportedLanguages) {
    this.supportedLanguages = supportedLanguages;
  }

  public List<SocialLinkEntity> getSocialLinks() {
    return socialLinks;
  }

  public void setSocialLinks(List<SocialLinkEntity> socialLinks) {
    this.socialLinks = socialLinks;
  }

  public List<CatalogEntity> getCatalogs() {
    return catalogs;
  }

  public void setCatalogs(List<CatalogEntity> catalogs) {
    this.catalogs = catalogs;
  }
}
