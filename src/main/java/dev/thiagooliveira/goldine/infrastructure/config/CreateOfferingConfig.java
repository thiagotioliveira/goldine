package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.CreateOffering;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateOfferingConfig {

  @Bean
  CreateOffering createOffering(BusinessRepository businessRepository) {
    return new CreateOffering(businessRepository);
  }
}
