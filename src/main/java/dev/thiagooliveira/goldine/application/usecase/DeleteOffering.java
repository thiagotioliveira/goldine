package dev.thiagooliveira.goldine.application.usecase;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class DeleteOffering {

  private final BusinessRepository businessRepository;

  public DeleteOffering(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public void execute(UUID businessId, UUID offeringId) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    business.removeOffering(offeringId);
    this.businessRepository.save(business);
  }
}
