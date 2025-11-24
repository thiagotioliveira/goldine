package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.DeleteOffering;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteOfferingConfig {

  @Bean
  DeleteOffering deleteOffering(BusinessRepository businessRepository) {
    return new DeleteOffering(businessRepository);
  }
}
