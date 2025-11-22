package dev.thiagooliveira.goldine.application.usecase.command;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.application.usecase.command.dto.CreateCatalogInput;
import dev.thiagooliveira.goldine.domain.model.Catalog;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class CreateCatalog {

  private final BusinessRepository businessRepository;

  public CreateCatalog(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Catalog execute(UUID businessId, CreateCatalogInput input) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var catalog = Catalog.create(input.name(), input.language());
    business.addCatalog(catalog);
    this.businessRepository.save(business);
    return catalog;
  }
}
