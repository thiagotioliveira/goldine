package dev.thiagooliveira.goldine.application.usecase;

import dev.thiagooliveira.goldine.application.exception.ApplicationException;
import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.application.exception.CategoryNotFoundException;
import dev.thiagooliveira.goldine.application.usecase.dto.UpdateCategoryInput;
import dev.thiagooliveira.goldine.domain.model.Category;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class UpdateCategory {

  private final BusinessRepository businessRepository;

  public UpdateCategory(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Category execute(
      UUID businessId, UUID originalCatalogId, UUID categoryId, UpdateCategoryInput input) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var category = business.findCategory(categoryId).orElseThrow(CategoryNotFoundException::new);
    business
        .findCategory(input.catalogId(), category.getLanguage(), input.name())
        .ifPresent(
            c -> {
              throw new ApplicationException(
                  String.format("Category %s already exists", input.name()));
            });
    category.update(input.name());
    if (!originalCatalogId.equals(input.catalogId())) {
      business.moveCategory(category, input.catalogId());
    }

    businessRepository.save(business);
    return category;
  }
}
