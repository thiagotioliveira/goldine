package dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business;

public class SocialLinkProjection {

  private final String type;
  private final String url;

  public SocialLinkProjection(String type, String url) {
    this.type = type;
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }
}
