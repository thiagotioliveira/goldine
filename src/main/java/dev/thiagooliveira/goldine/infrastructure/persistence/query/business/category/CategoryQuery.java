package dev.thiagooliveira.goldine.infrastructure.persistence.query.business.category;

import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.category.CategoryProjection;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CategoryQuery {

  private final EntityManager entityManager;

  public CategoryQuery(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<CategoryProjection> findAll(UUID businessId) {
    return entityManager
        .createQuery(
            "SELECT new dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.category.CategoryProjection(ct.language, ct.id, ct.name, c.id, c.name) FROM CategoryEntity c "
                + "JOIN c.catalog ct "
                + "WHERE ct.business.id = :businessId",
            CategoryProjection.class)
        .setParameter("businessId", businessId)
        .getResultList();
  }

  public Optional<CategoryProjection> findByBusinessIdAndId(UUID businessId, UUID id) {
    return entityManager
        .createQuery(
            "SELECT new dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.business.category.CategoryProjection(ct.language, ct.id, ct.name, c.id, c.name) FROM CategoryEntity c "
                + "JOIN c.catalog ct "
                + "WHERE ct.business.id = :businessId AND c.id = :id",
            CategoryProjection.class)
        .setParameter("businessId", businessId)
        .setParameter("id", id)
        .getResultList()
        .stream()
        .findFirst();
  }
}
