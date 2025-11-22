package dev.thiagooliveira.goldine.domain.model;

import java.util.Objects;

public class SocialLink {
  private SocialLinkType type;
  private String url;

  private SocialLink(SocialLinkType type, String url) {
    this.type = type;
    this.url = url;
  }

  public static SocialLink create(SocialLinkType type, String url) {
    return new SocialLink(type, url);
  }

  public static SocialLink load(SocialLinkType type, String url) {
    return new SocialLink(type, url);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    SocialLink that = (SocialLink) o;
    return type == that.type;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(type);
  }

  public SocialLinkType getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }
}
