package dev.thiagooliveira.goldine.infrastructure.persistence.business;

import dev.thiagooliveira.goldine.domain.model.Business;
import dev.thiagooliveira.goldine.domain.repository.BusinessRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class BusinessRepositoryAdapter implements BusinessRepository {

  private final BusinessEntityRepository businessEntityRepository;

  public BusinessRepositoryAdapter(BusinessEntityRepository businessEntityRepository) {
    this.businessEntityRepository = businessEntityRepository;
  }

  @Override
  public Optional<Business> findById(UUID id) {
    return businessEntityRepository.findById(id).map(BusinessEntity::toDomain);
  }

  @Override
  public Optional<Business> findByAlias(String alias) {
    return businessEntityRepository.findByAlias(alias).map(BusinessEntity::toDomain);
  }

  @Override
  public void save(Business business) {
    businessEntityRepository.save(BusinessEntity.fromDomain(business));
  }
}
