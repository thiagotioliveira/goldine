package dev.thiagooliveira.goldine.infrastructure.web.admin;

import dev.thiagooliveira.goldine.application.exception.ApplicationException;
import dev.thiagooliveira.goldine.application.usecase.CreateCatalog;
import dev.thiagooliveira.goldine.application.usecase.DeleteCatalog;
import dev.thiagooliveira.goldine.application.usecase.UpdateCatalog;
import dev.thiagooliveira.goldine.application.usecase.dto.CreateCatalogInput;
import dev.thiagooliveira.goldine.application.usecase.dto.UpdateCatalogInput;
import dev.thiagooliveira.goldine.domain.exception.DomainException;
import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.infrastructure.config.context.ApplicationContext;
import dev.thiagooliveira.goldine.infrastructure.persistence.query.business.catalog.CatalogQuery;
import dev.thiagooliveira.goldine.infrastructure.web.admin.dto.business.catalog.UpdateCatalogDTO;
import dev.thiagooliveira.goldine.infrastructure.web.exception.CatalogNotFoundException;
import dev.thiagooliveira.goldine.infrastructure.web.exception.WebException;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/catalogs")
public class AdminCatalogController {

  private final ApplicationContext applicationContext;
  private final CatalogQuery catalogQuery;
  private final CreateCatalog createCatalog;
  private final UpdateCatalog updateCatalog;
  private final DeleteCatalog deleteCatalog;

  public AdminCatalogController(
      ApplicationContext applicationContext,
      CatalogQuery catalogQuery,
      CreateCatalog createCatalog,
      UpdateCatalog updateCatalog,
      DeleteCatalog deleteCatalog) {
    this.applicationContext = applicationContext;
    this.catalogQuery = catalogQuery;
    this.createCatalog = createCatalog;
    this.updateCatalog = updateCatalog;
    this.deleteCatalog = deleteCatalog;
  }

  @GetMapping()
  public String index(Model model) {
    var catalogs = catalogQuery.findAll(applicationContext.getBusinessId());
    model.addAttribute("catalogs", catalogs);
    return "admin/business/catalog/catalog-list";
  }

  @GetMapping("/new")
  public String newCategory(Model model) {
    model.addAttribute("catalog", new UpdateCatalogDTO(applicationContext.getBusinessId()));
    return "admin/business/catalog/catalog-form";
  }

  @GetMapping("/edit/{id}")
  public String editCategory(@PathVariable("id") UUID id, Model model) {
    var catalog =
        catalogQuery
            .findByBusinessIdAndId(applicationContext.getBusinessId(), id)
            .orElseThrow(CatalogNotFoundException::new);
    model.addAttribute(
        "catalog", new UpdateCatalogDTO(applicationContext.getBusinessId(), catalog));
    return "admin/business/catalog/catalog-form";
  }

  @PostMapping("/delete")
  public String delete(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
    try {
      this.deleteCatalog.execute(applicationContext.getBusinessId(), id);
      redirectAttributes.addFlashAttribute("successMessage", "Catalog deleted successfully!");
    } catch (DomainException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/admin/catalogs";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute UpdateCatalogDTO updateCatalogDTO, Model model) {
    try {
      updateCatalogDTO.setBusinessId(applicationContext.getBusinessId());
      if (updateCatalogDTO.getId() == null) {
        createCatalog.execute(
            applicationContext.getBusinessId(),
            new CreateCatalogInput(
                Language.valueOf(updateCatalogDTO.getLanguage()), updateCatalogDTO.getName()));
        model.addAttribute("successMessage", "Catalog created successfully!");
      } else {
        updateCatalog.execute(
            applicationContext.getBusinessId(),
            updateCatalogDTO.getId(),
            new UpdateCatalogInput(
                updateCatalogDTO.getName(), Language.valueOf(updateCatalogDTO.getLanguage())));
        model.addAttribute("successMessage", "Catalog updated successfully!");
      }
      return index(model);
    } catch (DomainException | ApplicationException | WebException e) {
      model.addAttribute("catalog", updateCatalogDTO);
      model.addAttribute("errorMessage", e.getMessage());
    }
    return "admin/business/catalog/catalog-form";
  }
}
