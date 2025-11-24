package dev.thiagooliveira.goldine.infrastructure.persistence.query.business.offering;

import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.offering.OfferingProjection;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OfferingQuery {

  private final EntityManager entityManager;

  public OfferingQuery(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<OfferingProjection> findAll(UUID businessId) {
    return entityManager
        .createQuery(
            "SELECT new dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.offering.OfferingProjection(c.language, c.id, c.name, ct.id, ct.name, o.id, o.name, o.description, o.price) FROM OfferingEntity o "
                + "JOIN o.category ct "
                + "JOIN ct.catalog c "
                + "WHERE c.business.id = :businessId",
            OfferingProjection.class)
        .setParameter("businessId", businessId)
        .getResultList();
  }

  public Optional<OfferingProjection> findByBusinessIdAndId(UUID businessId, UUID id) {
    return entityManager
        .createQuery(
            "SELECT new dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.offering.OfferingProjection(c.language, c.id, c.name, ct.id, ct.name, o.id, o.name, o.description, o.price) FROM OfferingEntity o "
                + "JOIN o.category ct "
                + "JOIN ct.catalog c "
                + "WHERE c.business.id = :businessId "
                + "AND o.id = :id",
            OfferingProjection.class)
        .setParameter("businessId", businessId)
        .setParameter("id", id)
        .getResultList()
        .stream()
        .findFirst();
  }
}
