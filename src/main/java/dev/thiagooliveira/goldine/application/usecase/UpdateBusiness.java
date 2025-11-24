package dev.thiagooliveira.goldine.application.usecase;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.application.usecase.dto.UpdateBusinessInput;
import dev.thiagooliveira.goldine.domain.model.Business;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class UpdateBusiness {

  private final BusinessRepository businessRepository;

  public UpdateBusiness(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Business execute(UUID businessId, UpdateBusinessInput input) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    business.update(input.name(), input.address(), input.supportedLanguages(), input.socialLinks());
    this.businessRepository.save(business);
    return business;
  }
}
