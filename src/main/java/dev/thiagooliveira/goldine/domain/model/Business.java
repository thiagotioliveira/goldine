package dev.thiagooliveira.goldine.domain.model;

import dev.thiagooliveira.goldine.domain.exception.CatalogNotFoundException;
import dev.thiagooliveira.goldine.domain.exception.CategoryNotFoundException;
import dev.thiagooliveira.goldine.domain.exception.DomainException;
import java.net.URI;
import java.text.Normalizer;
import java.util.*;
import org.apache.commons.lang3.StringUtils;

public class Business {
  private UUID id;
  private String name;
  private String alias;
  private Optional<String> address;
  private Set<SocialLink> socialLinks;
  private Set<Language> supportedLanguages;
  private Set<Catalog> catalogs;

  private Business(
      UUID id,
      String name,
      String alias,
      Optional<String> address,
      Set<SocialLink> socialLinks,
      Set<Language> supportedLanguages,
      Set<Catalog> catalogs) {
    this.id = id;
    this.name = name;
    this.alias = alias;
    this.address = address;
    this.socialLinks = socialLinks != null ? new HashSet<>(socialLinks) : new HashSet<>();
    this.supportedLanguages =
        supportedLanguages != null ? new HashSet<>(supportedLanguages) : new HashSet<>();
    this.catalogs = catalogs != null ? new HashSet<>(catalogs) : new HashSet<>();
  }

  public static Business create(String name, Set<Language> supportedLanguages) {
    validateFieldName(name);
    return new Business(
        UUID.randomUUID(),
        name,
        generateAlias(name),
        Optional.empty(),
        Set.of(),
        supportedLanguages != null ? new HashSet<>(supportedLanguages) : new HashSet<>(),
        Set.of());
  }

  public static Business load(
      UUID id,
      String name,
      String alias,
      Optional<String> address,
      Set<SocialLink> socialLinks,
      Set<Language> supportedLanguages,
      Set<Catalog> catalogs) {
    return new Business(id, name, alias, address, socialLinks, supportedLanguages, catalogs);
  }

  public void update(
      String name, String address, Set<Language> supportedLanguages, Set<SocialLink> socialLinks) {
    validateFieldName(name);
    validateFieldName(address);
    validateSupportedLanguages(supportedLanguages);
    validateSocialLinks(socialLinks);
    this.name = name;
    this.address = Optional.ofNullable(address);
    this.supportedLanguages = supportedLanguages;
    this.socialLinks = socialLinks != null ? new HashSet<>(socialLinks) : new HashSet<>();
  }

  public void addSocialLink(SocialLink socialLink) {
    if (socialLink == null) throw new DomainException("Social link cannot be null");
    var existingLink =
        socialLinks.stream().filter(sl -> sl.getType().equals(socialLink.getType())).findFirst();

    existingLink.ifPresent(link -> socialLinks.remove(link));
    this.socialLinks.add(socialLink);
  }

  public void addCatalog(Catalog catalog) {
    if (catalog == null) throw new DomainException("Catalog cannot be null");
    if (catalog.getLanguage() == null) throw new DomainException("Language cannot be null");
    if (!supportedLanguages.contains(catalog.getLanguage()))
      throw new DomainException("Language not supported");
    this.catalogs.add(catalog);
  }

  public void removeCatalog(UUID catalogId) {
    var catalog =
        this.catalogs.stream()
            .filter(c -> c.getId().equals(catalogId))
            .findFirst()
            .orElseThrow(CatalogNotFoundException::new);
    if (!catalog.getCategories().isEmpty()) {
      throw new DomainException("Cannot remove catalog with categories");
    }
    catalogs.remove(catalog);
  }

  public void addCategory(UUID catalogId, Category category) {
    if (catalogId == null) {
      throw new DomainException("Catalog ID cannot be null");
    }
    if (category == null) {
      throw new DomainException("Category cannot be null");
    }
    findCategory(catalogId, category.getLanguage(), category.getName())
        .ifPresent(
            c -> {
              throw new DomainException(
                  String.format("Category %s already exists", category.getName()));
            });
    var catalog = findCatalog(catalogId).orElseThrow(CatalogNotFoundException::new);
    catalog.addCategory(category);
  }

  public void moveCategory(UUID categoryId, UUID newCatalogId) {
    var category = findCategory(categoryId).orElseThrow(CategoryNotFoundException::new);
    for (Catalog catalog : catalogs) {
      catalog.getCategories().removeIf(cat -> cat.getId().equals(category.getId()));
    }
    addCategory(newCatalogId, category);
  }

  public void removeCategory(UUID categoryId) {
    var category = findCategory(categoryId).orElseThrow(CategoryNotFoundException::new);
    if (!category.getOfferings().isEmpty()) {
      throw new DomainException("Cannot remove category with offerings");
    }
    for (Catalog catalog : catalogs) {
      if (catalog.removeCategory(categoryId)) break;
    }
  }

  public void addOffering(UUID categoryId, Offering offering) {
    if (offering == null) {
      throw new DomainException("Offering cannot be null");
    }
    findOffering(offering.getLanguage(), offering.getName())
        .ifPresent(
            o -> {
              throw new DomainException(
                  String.format("Offering %s already exists", offering.getName()));
            });
    var category = findCategory(categoryId).orElseThrow(CategoryNotFoundException::new);
    category.addOffering(offering);
  }

  public void removeOffering(UUID offeringId) {
    if (offeringId == null) {
      throw new DomainException("Offering ID cannot be null");
    }
    externalFor:
    for (Catalog catalog : catalogs) {
      for (Category cat : catalog.getCategories()) {
        if (cat.removeOffering(offeringId)) break externalFor;
      }
    }
  }

  public Optional<Catalog> findCatalog(UUID catalogId) {
    return this.catalogs.stream().filter(c -> c.getId().equals(catalogId)).findFirst();
  }

  public Optional<Category> findCategory(UUID categoryId) {
    return this.catalogs.stream()
        .flatMap(catalog -> catalog.getCategories().stream())
        .filter(category -> category.getId().equals(categoryId))
        .findFirst();
  }

  public Optional<Category> findCategory(UUID catalogId, Language language, String categoryName) {
    return this.catalogs.stream()
        .filter(c -> c.getId().equals(catalogId))
        .flatMap(catalog -> catalog.getCategories().stream())
        .filter(
            category ->
                category.getName().equals(categoryName) && category.getLanguage().equals(language))
        .findFirst();
  }

  public Optional<Offering> findOffering(Language language, String offeringName) {
    return this.catalogs.stream()
        .flatMap(catalog -> catalog.getCategories().stream())
        .flatMap(category -> category.getOfferings().stream())
        .filter(
            offering ->
                offering.getName().equals(offeringName) && offering.getLanguage().equals(language))
        .findFirst();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Business business = (Business) o;
    return Objects.equals(id, business.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAlias() {
    return alias;
  }

  public Optional<String> getAddress() {
    return address;
  }

  public Set<SocialLink> getSocialLinks() {
    return Collections.unmodifiableSet(socialLinks);
  }

  public Set<Language> getSupportedLanguages() {
    return Collections.unmodifiableSet(supportedLanguages);
  }

  public Set<Catalog> getCatalogs() {
    return Collections.unmodifiableSet(catalogs);
  }

  private static String generateAlias(String name) {
    if (name == null) return "";
    // Remove accents
    String normalized = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    // Remove special characters and replace spaces with hyphens
    return normalized.replaceAll("[^\\w\\s-]", "").trim().replaceAll("\\s+", "-").toLowerCase();
  }

  private static void validateFieldName(String fieldName) {
    if (StringUtils.isBlank(fieldName)) throw new DomainException(fieldName + " cannot be null");
    if (fieldName.length() < 3)
      throw new DomainException(fieldName + " cannot be less than 3 characters");
  }

  private static void validateSupportedLanguages(Set<Language> supportedLanguages) {
    if (supportedLanguages == null || supportedLanguages.isEmpty()) {
      throw new DomainException("Business must have at least one supported language");
    }
  }

  private static void validateSocialLinks(Set<SocialLink> socialLinks) {
    if (socialLinks != null && !socialLinks.isEmpty()) {
      for (SocialLink socialLink : socialLinks) {
        try {
          URI.create(socialLink.getUrl());
        } catch (IllegalArgumentException e) {
          throw new DomainException("Invalid URL for social link: " + socialLink.getUrl());
        }
      }
    }
  }
}
