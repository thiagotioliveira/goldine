package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.UpdateCategory;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCategoryConfig {

  @Bean
  UpdateCategory updateCategory(BusinessRepository businessRepository) {
    return new UpdateCategory(businessRepository);
  }
}
