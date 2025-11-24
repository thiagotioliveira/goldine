package dev.thiagooliveira.goldine.application.usecase;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class DeleteCategory {

  private final BusinessRepository businessRepository;

  public DeleteCategory(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public void execute(UUID businessId, UUID categoryId) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    business.removeCategory(categoryId);
    this.businessRepository.save(business);
  }
}
