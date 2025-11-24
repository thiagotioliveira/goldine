package dev.thiagooliveira.goldine.infrastructure.config.seed;

import dev.thiagooliveira.goldine.application.usecase.*;
import dev.thiagooliveira.goldine.application.usecase.dto.*;
import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.domain.model.SocialLink;
import dev.thiagooliveira.goldine.domain.model.SocialLinkType;
import dev.thiagooliveira.goldine.infrastructure.config.context.ApplicationContext;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApplicationInitDataRunner implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(ApplicationInitDataRunner.class);

  @Autowired private SeedProperties seedProperties;

  @Autowired private ApplicationContext applicationContext;

  @Autowired private CreateBusiness createBusiness;

  @Autowired private UpdateBusiness updateBusiness;

  @Autowired private CreateCatalog createCatalog;

  @Autowired private CreateCategory createCategory;

  @Autowired private CreateOffering createOffering;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    SeedProperties.Business businessConfig = seedProperties.getBusiness();

    var business =
        createBusiness.execute(
            new CreateBusinessInput(
                businessConfig.getName(),
                businessConfig.getSupportedLanguages().stream()
                    .map(Language::valueOf)
                    .collect(Collectors.toSet())));

    applicationContext.setBusinessId(business.getId());

    business =
        updateBusiness.execute(
            business.getId(),
            new UpdateBusinessInput(
                business.getName(),
                businessConfig.getAddress(),
                business.getSupportedLanguages(),
                businessConfig.getSocialLinks().stream()
                    .map(s -> SocialLink.create(SocialLinkType.valueOf(s.getType()), s.getUrl()))
                    .collect(Collectors.toSet())));

    for (SeedProperties.Catalog catalogConfig : businessConfig.getCatalogs()) {
      var catalog =
          createCatalog.execute(
              business.getId(),
              new CreateCatalogInput(
                  Language.valueOf(catalogConfig.getLanguage()), catalogConfig.getName()));

      for (SeedProperties.Category categoryConfig : catalogConfig.getCategories()) {
        var category =
            createCategory.execute(
                business.getId(),
                new CreateCategoryInput(
                    catalog.getId(), catalog.getLanguage(), categoryConfig.getName()));

        for (SeedProperties.Offering offeringConfig : categoryConfig.getOfferings()) {
          var offering =
              createOffering.execute(
                  business.getId(),
                  catalog.getId(),
                  category.getId(),
                  new CreateOfferingInput(
                      category.getLanguage(),
                      offeringConfig.getName(),
                      offeringConfig.getDescription(),
                      offeringConfig.getPrice(),
                      offeringConfig.getImages()));
        }
      }
    }

    log.info(
        "\nBusiness created!\nId: {}\nName: {}\nAlias: {}\nAddres: {}\nSupported languages: {}",
        business.getId(),
        business.getName(),
        business.getAlias(),
        business.getAddress().orElse(null),
        business.getSupportedLanguages());
  }
}
