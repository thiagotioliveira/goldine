package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.UpdateOffering;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateOfferingConfig {

  @Bean
  UpdateOffering updateOffering(BusinessRepository businessRepository) {
    return new UpdateOffering(businessRepository);
  }
}
