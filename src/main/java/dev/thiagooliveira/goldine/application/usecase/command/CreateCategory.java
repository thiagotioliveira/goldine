package dev.thiagooliveira.goldine.application.usecase.command;

import dev.thiagooliveira.goldine.application.exception.BusinessNotFoundException;
import dev.thiagooliveira.goldine.application.usecase.command.dto.CreateCategoryInput;
import dev.thiagooliveira.goldine.domain.model.Category;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.UUID;

public class CreateCategory {

  private final BusinessRepository businessRepository;

  public CreateCategory(BusinessRepository businessRepository) {
    this.businessRepository = businessRepository;
  }

  public Category execute(UUID businessId, CreateCategoryInput input) {
    var business =
        this.businessRepository.findById(businessId).orElseThrow(BusinessNotFoundException::new);
    var category = Category.create(input.language(), input.name());
    business.addCategory(input.catalogId(), category);
    businessRepository.save(business);
    return category;
  }
}
