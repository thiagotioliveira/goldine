package dev.thiagooliveira.goldine.infrastructure.web.business.dto;

import static dev.thiagooliveira.goldine.infrastructure.util.Constants.*;

import dev.thiagooliveira.goldine.domain.model.Business;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessDTO {
  private final String colorPrimary;
  private final String colorSecondary;
  private final String backgroundColorPrimary;
  private final String backgroundColorSecondary;

  private final String name;
  private final String address;
  private final String alias;
  private final List<String> supportedLanguages;
  private final List<SocialLinkDTO> socialLinks;
  private final List<CatalogDTO> catalogs;

  public BusinessDTO(Business business) {
    this.colorPrimary = COLOR_PRIMARY;
    this.colorSecondary = COLOR_SECONDARY;
    this.backgroundColorPrimary = BACKGROUND_COLOR_PRIMARY;
    this.backgroundColorSecondary = BACKGROUND_COLOR_SECONDARY;
    this.name = business.getName();
    this.address = business.getAddress().orElse(null);
    this.alias = business.getAlias();
    this.supportedLanguages =
        business.getSupportedLanguages().stream().map(Enum::name).collect(Collectors.toList());
    this.socialLinks =
        business.getSocialLinks().stream().map(SocialLinkDTO::new).collect(Collectors.toList());
    this.catalogs =
        business.getCatalogs().stream().map(CatalogDTO::new).collect(Collectors.toList());
  }

  public String getColorPrimary() {
    return colorPrimary;
  }

  public String getColorSecondary() {
    return colorSecondary;
  }

  public String getBackgroundColorPrimary() {
    return backgroundColorPrimary;
  }

  public String getBackgroundColorSecondary() {
    return backgroundColorSecondary;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getAlias() {
    return alias;
  }

  public List<String> getSupportedLanguages() {
    return supportedLanguages;
  }

  public List<SocialLinkDTO> getSocialLinks() {
    return socialLinks;
  }

  public List<CatalogDTO> getCatalogs() {
    return catalogs;
  }
}
