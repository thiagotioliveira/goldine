package dev.thiagooliveira.goldine.infrastructure.persistence.query.business.catalog;

import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.catalog.CatalogProjection;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CatalogQuery {

  private final EntityManager entityManager;

  public CatalogQuery(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<CatalogProjection> findAll(UUID businessId) {
    return entityManager
        .createQuery(
            "SELECT new dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.catalog.CatalogProjection(c.id, c.name, c.language) "
                + "FROM CatalogEntity c "
                + "WHERE c.business.id = :businessId",
            CatalogProjection.class)
        .setParameter("businessId", businessId)
        .getResultList();
  }

  public Optional<CatalogProjection> findByBusinessIdAndId(UUID businessId, UUID id) {
    return entityManager
        .createQuery(
            "SELECT new dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.catalog.CatalogProjection(c.id, c.name, c.language) "
                + "FROM CatalogEntity c "
                + "WHERE c.business.id = :businessId "
                + "AND c.id = :id",
            CatalogProjection.class)
        .setParameter("businessId", businessId)
        .setParameter("id", id)
        .getResultList()
        .stream()
        .findFirst();
  }
}
