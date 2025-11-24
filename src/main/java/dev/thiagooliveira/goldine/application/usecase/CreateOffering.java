package dev.thiagooliveira.goldine.application.usecase;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.application.usecase.dto.CreateOfferingInput;
import dev.thiagooliveira.goldine.domain.model.Offering;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class CreateOffering {

  private final BusinessRepository businessRepository;

  public CreateOffering(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Offering execute(
      UUID businessId, UUID catalogId, UUID categoryId, CreateOfferingInput input) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var offering =
        Offering.create(
            input.language(), input.price(), input.name(), input.description(), input.images());
    business.addOffering(categoryId, offering);
    this.businessRepository.save(business);
    return offering;
  }
}
