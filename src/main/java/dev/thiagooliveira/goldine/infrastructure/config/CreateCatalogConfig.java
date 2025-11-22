package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.command.CreateCatalog;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateCatalogConfig {

  @Bean
  CreateCatalog createCatalog(BusinessRepository businessRepository) {
    return new CreateCatalog(businessRepository);
  }
}
