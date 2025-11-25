package dev.thiagooliveira.goldine.infrastructure.config.context;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContext {

  private UUID businessId;

  public UUID getBusinessId() {
    return businessId;
  }

  public void setBusinessId(UUID businessId) {
    if (this.businessId == null) {
      this.businessId = businessId;
    }
  }
}
