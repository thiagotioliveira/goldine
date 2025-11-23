package dev.thiagooliveira.goldine.infrastructure.persistence.query.projection;

import dev.thiagooliveira.goldine.domain.model.Language;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BusinessProjection {
  private final UUID id;
  private final String alias;
  private final String name;
  private final String address;
  private final Set<Language> supportedLanguages;
  private final List<SocialLinkProjection> socialLinks;

  public BusinessProjection(
      UUID id,
      String alias,
      String name,
      String address,
      Set<Language> supportedLanguages,
      List<SocialLinkProjection> socialLinks) {
    this.id = id;
    this.alias = alias;
    this.name = name;
    this.address = address;
    this.supportedLanguages = supportedLanguages;
    this.socialLinks = socialLinks;
  }

  public UUID getId() {
    return id;
  }

  public String getAlias() {
    return alias;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public Set<Language> getSupportedLanguages() {
    return supportedLanguages;
  }

  public List<SocialLinkProjection> getSocialLinks() {
    return socialLinks;
  }
}
