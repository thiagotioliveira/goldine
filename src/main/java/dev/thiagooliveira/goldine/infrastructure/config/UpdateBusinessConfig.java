package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.command.UpdateBusiness;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateBusinessConfig {

  @Bean
  UpdateBusiness updateBusiness(BusinessRepository businessRepository) {
    return new UpdateBusiness(businessRepository);
  }
}
