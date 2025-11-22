package dev.thiagooliveira.goldine.infrastructure.config;

import dev.thiagooliveira.goldine.application.usecase.command.GetBusiness;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetBusinessConfig {

  @Bean
  GetBusiness getBusiness(BusinessRepository businessRepository) {
    return new GetBusiness(businessRepository);
  }
}
