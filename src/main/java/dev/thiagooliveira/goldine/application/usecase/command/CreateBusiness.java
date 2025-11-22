package dev.thiagooliveira.goldine.application.usecase.command;

import dev.thiagooliveira.goldine.application.exception.ApplicationException;
import dev.thiagooliveira.goldine.application.usecase.command.dto.CreateBusinessInput;
import dev.thiagooliveira.goldine.domain.model.Business;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;

public class CreateBusiness {

  private final BusinessRepository businessRepository;

  public CreateBusiness(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Business execute(CreateBusinessInput input) {
    var business = Business.create(input.name(), input.supportedLanguages());
    businessRepository
        .findByAlias(business.getAlias())
        .ifPresent(
            b -> {
              throw new ApplicationException("Business already exists");
            });
    businessRepository.save(business);
    return business;
  }
}
