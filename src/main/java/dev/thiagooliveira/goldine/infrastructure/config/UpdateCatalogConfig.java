package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.UpdateCatalog;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateCatalogConfig {

  @Bean
  UpdateCatalog updateCatalog(BusinessRepository businessRepository) {
    return new UpdateCatalog(businessRepository);
  }
}
