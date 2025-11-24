package dev.thiagooliveira.goldine.application.usecase;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.application.exception.CatalogNotFoundException;
import dev.thiagooliveira.goldine.application.usecase.dto.UpdateCatalogInput;
import dev.thiagooliveira.goldine.domain.model.Catalog;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class UpdateCatalog {

  private final BusinessRepository businessRepository;

  public UpdateCatalog(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Catalog execute(UUID businessId, UUID catalogId, UpdateCatalogInput input) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var catalog = business.findCatalog(catalogId).orElseThrow(CatalogNotFoundException::new);
    catalog.update(input.name(), input.language());
    businessRepository.save(business);
    return catalog;
  }
}
