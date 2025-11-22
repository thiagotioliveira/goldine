package dev.thiagooliveira.goldine.domain.repository;

import dev.thiagooliveira.goldine.domain.model.Business;
import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository {
  Optional<Business> findById(UUID id);

  Optional<Business> findByAlias(String alias);

  void save(Business business);
}
