package dev.thiagooliveira.goldine.infrastructure.config.seed;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.seed")
public class SeedProperties {

  private Business business;

  public Business getBusiness() {
    return business;
  }

  public void setBusiness(Business business) {
    this.business = business;
  }

  public static class Business {
    private String name;
    private String alias;
    private String address;
    private List<String> supportedLanguages;
    private List<SocialLink> socialLinks = List.of();
    private List<Catalog> catalogs = List.of();

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

    public List<String> getSupportedLanguages() {
      return supportedLanguages;
    }

    public void setSupportedLanguages(List<String> supportedLanguages) {
      this.supportedLanguages = supportedLanguages;
    }

    public List<SocialLink> getSocialLinks() {
      return socialLinks;
    }

    public void setSocialLinks(List<SocialLink> socialLinks) {
      this.socialLinks = socialLinks;
    }

    public List<Catalog> getCatalogs() {
      return catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
      this.catalogs = catalogs;
    }
  }

  public static class SocialLink {
    private String type;
    private String url;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }
  }

  public static class Catalog {
    private String name;
    private String language;
    private List<Category> categories;

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

    public List<Category> getCategories() {
      return categories;
    }

    public void setCategories(List<Category> categories) {
      this.categories = categories;
    }
  }

  public static class Category {
    private String name;
    private List<Offering> offerings;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<Offering> getOfferings() {
      return offerings;
    }

    public void setOfferings(List<Offering> offerings) {
      this.offerings = offerings;
    }
  }

  public static class Offering {
    private String name;
    private String description;
    private BigDecimal price;
    private List<String> images;

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

    public List<String> getImages() {
      return images;
    }

    public void setImages(List<String> images) {
      this.images = images;
    }
  }
}
