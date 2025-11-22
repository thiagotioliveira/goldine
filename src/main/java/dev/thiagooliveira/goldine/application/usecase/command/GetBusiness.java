package dev.thiagooliveira.goldine.application.usecase.command;

import dev.thiagooliveira.goldine.domain.model.Business;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.Optional;

public class GetBusiness {

  private final BusinessRepository businessRepository;

  public GetBusiness(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Optional<Business> execute(String alias) {
    return businessRepository.findByAlias(alias);
  }
}
