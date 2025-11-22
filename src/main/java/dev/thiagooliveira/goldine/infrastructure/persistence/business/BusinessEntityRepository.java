package dev.thiagooliveira.goldine.infrastructure.persistence.business;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessEntityRepository extends JpaRepository<BusinessEntity, UUID> {

  Optional<BusinessEntity> findByAlias(String alias);
}
