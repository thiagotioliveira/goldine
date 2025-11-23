package dev.thiagooliveira.goldine.infrastructure.web.admin.dto;

import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.BusinessProjection;
import java.util.List;
import java.util.stream.Collectors;

public class EditBusinessDTO {

  private String name;
  private String address;
  private List<String> supportedLanguages;
  private List<SocialLinkDTO> socialLinks;

  public EditBusinessDTO() {}

  public EditBusinessDTO(BusinessProjection projection) {
    this.name = projection.getName();
    this.address = projection.getAddress();
    this.supportedLanguages = projection.getSupportedLanguages().stream().map(Enum::name).toList();
    this.socialLinks =
        projection.getSocialLinks().stream()
            .map(s -> new SocialLinkDTO(s.getType(), s.getUrl()))
            .collect(Collectors.toList());
  }

  public static class SocialLinkDTO {
    private String type;
    private String url;

    public SocialLinkDTO() {}

    public SocialLinkDTO(String type, String url) {
      this.type = type;
      this.url = url;
    }

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public List<SocialLinkDTO> getSocialLinks() {
    return socialLinks;
  }

  public void setSocialLinks(List<SocialLinkDTO> socialLinks) {
    this.socialLinks = socialLinks;
  }
}
