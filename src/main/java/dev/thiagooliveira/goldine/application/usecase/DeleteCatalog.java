package dev.thiagooliveira.goldine.application.usecase;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class DeleteCatalog {

  private final BusinessRepository businessRepository;

  public DeleteCatalog(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public void execute(UUID businessId, UUID catalogId) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    business.removeCatalog(catalogId);
    businessRepository.save(business);
  }
}
