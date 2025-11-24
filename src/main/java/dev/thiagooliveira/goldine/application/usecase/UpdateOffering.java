package dev.thiagooliveira.goldine.application.usecase;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.application.exception.OfferingNotFoundException;
import dev.thiagooliveira.goldine.application.usecase.dto.UpdateOfferingInput;
import dev.thiagooliveira.goldine.domain.model.Offering;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class UpdateOffering {

  private final BusinessRepository businessRepository;

  public UpdateOffering(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Offering execute(
      UUID businessId, UUID originalCategoryId, UUID offeringId, UpdateOfferingInput input) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var offering = business.findOffering(offeringId).orElseThrow(OfferingNotFoundException::new);
    offering.update(input.price(), input.name(), input.description());
    if (!originalCategoryId.equals(input.categoryId())) {
      business.moveOffering(offering, input.categoryId());
    }
    businessRepository.save(business);
    return offering;
  }
}
