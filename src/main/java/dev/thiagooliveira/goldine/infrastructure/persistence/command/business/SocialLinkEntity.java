package dev.thiagooliveira.goldine.infrastructure.persistence.command.business;

import dev.thiagooliveira.goldine.domain.model.SocialLink;
import dev.thiagooliveira.goldine.domain.model.SocialLinkType;
import jakarta.persistence.*;

@Entity
@Table(name = "social_link")
public class SocialLinkEntity {

  @EmbeddedId private SocialLinkKey id;

  private String url;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("businessId")
  @JoinColumn(name = "business_id", nullable = false)
  private BusinessEntity business;

  public SocialLinkEntity() {}

  public SocialLinkEntity(String type, String url) {
    this.id = new SocialLinkKey();
    this.id.setType(type);
    this.url = url;
  }

  public static SocialLinkEntity fromDomain(SocialLink socialLink) {
    return new SocialLinkEntity(socialLink.getType().name(), socialLink.getUrl());
  }

  public SocialLink toDomain() {
    return SocialLink.load(SocialLinkType.valueOf(id.getType()), url);
  }

  public SocialLinkKey getId() {
    return id;
  }

  public void setId(SocialLinkKey id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public BusinessEntity getBusiness() {
    return business;
  }

  public void setBusiness(BusinessEntity business) {
    this.business = business;
    if (this.id == null) {
      this.id = new SocialLinkKey();
    }
    this.id.setBusinessId(business.getId());
  }
}
