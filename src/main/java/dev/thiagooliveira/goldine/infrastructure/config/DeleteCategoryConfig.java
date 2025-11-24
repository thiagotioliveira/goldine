package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.DeleteCategory;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteCategoryConfig {

  @Bean
  DeleteCategory deleteCategory(BusinessRepository businessRepository) {
    return new DeleteCategory(businessRepository);
  }
}
