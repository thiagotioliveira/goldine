package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.DeleteCatalog;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteCatalogConfig {

  @Bean
  DeleteCatalog deleteCatalog(BusinessRepository businessRepository) {
    return new DeleteCatalog(businessRepository);
  }
}
