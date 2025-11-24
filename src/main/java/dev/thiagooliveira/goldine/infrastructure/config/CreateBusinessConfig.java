package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.CreateBusiness;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateBusinessConfig {

  @Bean
  CreateBusiness createBusiness(BusinessRepository businessRepository) {
    return new CreateBusiness(businessRepository);
  }
}
