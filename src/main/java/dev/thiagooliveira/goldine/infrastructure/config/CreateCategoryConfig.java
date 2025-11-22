package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.command.CreateCategory;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateCategoryConfig {

  @Bean
  CreateCategory createCategory(BusinessRepository businessRepository) {
    return new CreateCategory(businessRepository);
  }
}
