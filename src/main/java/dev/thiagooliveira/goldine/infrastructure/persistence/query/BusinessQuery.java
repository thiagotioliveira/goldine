package dev.thiagooliveira.goldine.infrastructure.persistence.query;

import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.infrastructure.persistence.command.business.BusinessEntity;
import dev.thiagooliveira.goldine.infrastructure.persistence.command.business.SocialLinkEntity;
import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.BusinessProjection;
import dev.thiagooliveira.goldine.infrastructure.persistence.query.projection.SocialLinkProjection;
import jakarta.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class BusinessQuery {

  private final EntityManager entityManager;

  public BusinessQuery(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Optional<BusinessProjection> findById(UUID id) {
    var query =
        entityManager.createQuery(
            "SELECT b, l, s "
                + "FROM BusinessEntity b "
                + "LEFT JOIN b.supportedLanguages l "
                + "LEFT JOIN b.socialLinks s "
                + "WHERE b.id = :id",
            Object[].class);

    query.setParameter("id", id);

    List<Object[]> results = query.getResultList();
    if (results.isEmpty()) return Optional.empty();

    BusinessEntity business = (BusinessEntity) results.get(0)[0];

    Set<Language> languages =
        results.stream()
            .map(row -> (Language) row[1])
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    List<SocialLinkProjection> socialLinks =
        results.stream()
            .map(row -> (SocialLinkEntity) row[2])
            .filter(Objects::nonNull)
            .map(sl -> new SocialLinkProjection(sl.getId().getType(), sl.getUrl()))
            .distinct()
            .collect(Collectors.toList());

    BusinessProjection projection =
        new BusinessProjection(
            business.getId(),
            business.getAlias(),
            business.getName(),
            business.getAddress(),
            languages,
            socialLinks);

    return Optional.of(projection);
  }
}
