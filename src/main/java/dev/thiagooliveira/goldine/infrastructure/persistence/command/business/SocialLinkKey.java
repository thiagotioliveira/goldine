package dev.thiagooliveira.goldine.infrastructure.persistence.command.business;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class SocialLinkKey implements Serializable {
  @Column private UUID businessId;

  private String type;

  public SocialLinkKey() {}

  public SocialLinkKey(UUID businessId, String type) {
    this.businessId = businessId;
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    SocialLinkKey that = (SocialLinkKey) o;
    return Objects.equals(businessId, that.businessId) && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(businessId, type);
  }

  public UUID getBusinessId() {
    return businessId;
  }

  public void setBusinessId(UUID businessId) {
    this.businessId = businessId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
