package dev.thiagooliveira.goldine.infrastructure.web.dto;

import dev.thiagooliveira.goldine.domain.model.SocialLink;

public class SocialLinkDTO {
  private final String type;
  private final String url;

  private SocialLinkDTO(String type, String url) {
    this.type = type;
    this.url = url;
  }

  public SocialLinkDTO(SocialLink link) {
    this(link.getType().name().toLowerCase(), link.getUrl());
  }

  public String getType() {
    return type;
  }

  public String getUrl() {
    return url;
  }
}
