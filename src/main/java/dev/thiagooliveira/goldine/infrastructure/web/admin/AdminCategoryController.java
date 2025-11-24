package dev.thiagooliveira.goldine.infrastructure.web.admin;

import dev.thiagooliveira.goldine.application.exception.ApplicationException;
import dev.thiagooliveira.goldine.application.usecase.CreateCategory;
import dev.thiagooliveira.goldine.application.usecase.DeleteCategory;
import dev.thiagooliveira.goldine.application.usecase.UpdateCategory;
import dev.thiagooliveira.goldine.application.usecase.dto.CreateCategoryInput;
import dev.thiagooliveira.goldine.application.usecase.dto.UpdateCategoryInput;
import dev.thiagooliveira.goldine.domain.exception.DomainException;
import dev.thiagooliveira.goldine.infrastructure.config.context.ApplicationContext;
import dev.thiagooliveira.goldine.infrastructure.persistence.query.business.catalog.CatalogQuery;
import dev.thiagooliveira.goldine.infrastructure.persistence.query.business.category.CategoryQuery;
import dev.thiagooliveira.goldine.infrastructure.web.admin.dto.business.category.UpdateCategoryDTO;
import dev.thiagooliveira.goldine.infrastructure.web.exception.CatalogNotFoundException;
import dev.thiagooliveira.goldine.infrastructure.web.exception.WebException;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

  private final ApplicationContext applicationContext;
  private final CategoryQuery categoryQuery;
  private final CatalogQuery catalogQuery;
  private final CreateCategory createCategory;
  private final UpdateCategory updateCategory;
  private final DeleteCategory deleteCategory;

  public AdminCategoryController(
      ApplicationContext applicationContext,
      CategoryQuery categoryQuery,
      CatalogQuery catalogQuery,
      CreateCategory createCategory,
      UpdateCategory updateCategory,
      DeleteCategory deleteCategory) {
    this.applicationContext = applicationContext;
    this.categoryQuery = categoryQuery;
    this.catalogQuery = catalogQuery;
    this.createCategory = createCategory;
    this.updateCategory = updateCategory;
    this.deleteCategory = deleteCategory;
  }

  @GetMapping
  public String index(Model model) {
    var categories = categoryQuery.findAll(applicationContext.getBusinessId());
    model.addAttribute("categories", categories);
    return "admin/business/category/category-list";
  }

  @GetMapping("/new")
  public String newCategory(Model model) {
    model.addAttribute("category", new UpdateCategoryDTO());
    model.addAttribute("catalogs", catalogQuery.findAll(applicationContext.getBusinessId()));
    return "admin/business/category/category-form";
  }

  @GetMapping("/edit/{id}")
  public String editCategory(@PathVariable("id") UUID id, Model model) {
    var category =
        categoryQuery
            .findByBusinessIdAndId(applicationContext.getBusinessId(), id)
            .orElseThrow(CatalogNotFoundException::new);
    model.addAttribute("category", new UpdateCategoryDTO(category));
    model.addAttribute("catalogs", catalogQuery.findAll(applicationContext.getBusinessId()));
    return "admin/business/category/category-form";
  }

  @PostMapping("/delete")
  public String delete(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
    try {
      this.deleteCategory.execute(applicationContext.getBusinessId(), id);
      redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
    } catch (DomainException | ApplicationException | WebException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/admin/categories";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute UpdateCategoryDTO updateCategoryDTO, Model model) {
    try {
      var catalog =
          catalogQuery
              .findByBusinessIdAndId(
                  applicationContext.getBusinessId(), updateCategoryDTO.getCatalogId())
              .orElseThrow(CatalogNotFoundException::new);
      if (updateCategoryDTO.getId() == null) {
        createCategory.execute(
            applicationContext.getBusinessId(),
            new CreateCategoryInput(
                catalog.getId(), catalog.getLanguage(), updateCategoryDTO.getName()));
        model.addAttribute("successMessage", "Category created successfully!");
      } else {
        updateCategory.execute(
            applicationContext.getBusinessId(),
            updateCategoryDTO.getOriginalCatalogId(),
            updateCategoryDTO.getId(),
            new UpdateCategoryInput(updateCategoryDTO.getCatalogId(), updateCategoryDTO.getName()));
        model.addAttribute("successMessage", "Category updated successfully!");
      }
      return index(model);
    } catch (DomainException | ApplicationException | WebException e) {
      model.addAttribute("category", updateCategoryDTO);
      model.addAttribute("errorMessage", e.getMessage());
      return "admin/business/category/category-form";
    }
  }
}
