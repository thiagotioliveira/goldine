package dev.thiagooliveira.goldine.infrastructure.web.admin;

import dev.thiagooliveira.goldine.application.exception.ApplicationException;
import dev.thiagooliveira.goldine.application.usecase.CreateOffering;
import dev.thiagooliveira.goldine.application.usecase.DeleteOffering;
import dev.thiagooliveira.goldine.application.usecase.UpdateOffering;
import dev.thiagooliveira.goldine.application.usecase.dto.CreateOfferingInput;
import dev.thiagooliveira.goldine.application.usecase.dto.UpdateOfferingInput;
import dev.thiagooliveira.goldine.domain.exception.DomainException;
import dev.thiagooliveira.goldine.infrastructure.config.context.ApplicationContext;
import dev.thiagooliveira.goldine.infrastructure.persistence.query.business.category.CategoryQuery;
import dev.thiagooliveira.goldine.infrastructure.persistence.query.business.offering.OfferingQuery;
import dev.thiagooliveira.goldine.infrastructure.web.admin.dto.business.offering.UpdateOfferingDTO;
import dev.thiagooliveira.goldine.infrastructure.web.exception.CategoryNotFoundException;
import dev.thiagooliveira.goldine.infrastructure.web.exception.OfferingNotFoundException;
import dev.thiagooliveira.goldine.infrastructure.web.exception.WebException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/offerings")
public class AdminOfferingController {

  private final ApplicationContext applicationContext;
  private final OfferingQuery offeringQuery;
  private final CreateOffering createOffering;
  private final UpdateOffering updateOffering;
  private final DeleteOffering deleteOffering;
  private final CategoryQuery categoryQuery;

  public AdminOfferingController(
      ApplicationContext applicationContext,
      OfferingQuery offeringQuery,
      CreateOffering createOffering,
      UpdateOffering updateOffering,
      DeleteOffering deleteOffering,
      CategoryQuery categoryQuery) {
    this.applicationContext = applicationContext;
    this.offeringQuery = offeringQuery;
    this.createOffering = createOffering;
    this.updateOffering = updateOffering;
    this.deleteOffering = deleteOffering;
    this.categoryQuery = categoryQuery;
  }

  @GetMapping
  public String index(Model model) {
    var offerings = offeringQuery.findAll(applicationContext.getBusinessId());
    model.addAttribute("offerings", offerings);
    return "admin/business/offering/offering-list";
  }

  @GetMapping("/new")
  public String newOffering(Model model) {
    model.addAttribute("offering", new UpdateOfferingDTO());
    model.addAttribute("categories", categoryQuery.findAll(applicationContext.getBusinessId()));
    return "admin/business/offering/offering-form";
  }

  @GetMapping("/edit/{id}")
  public String editOffering(@PathVariable("id") UUID id, Model model) {
    var offering =
        offeringQuery
            .findByBusinessIdAndId(applicationContext.getBusinessId(), id)
            .orElseThrow(OfferingNotFoundException::new);
    model.addAttribute("offering", new UpdateOfferingDTO(offering));
    model.addAttribute("categories", categoryQuery.findAll(applicationContext.getBusinessId()));
    return "admin/business/offering/offering-form";
  }

  @PostMapping("/delete")
  public String delete(@RequestParam("id") UUID id, RedirectAttributes redirectAttributes) {
    try {
      this.deleteOffering.execute(applicationContext.getBusinessId(), id);
      redirectAttributes.addFlashAttribute("successMessage", "Offering deleted successfully!");
    } catch (DomainException | ApplicationException | WebException e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/admin/offerings";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute UpdateOfferingDTO updateOfferingDTO, Model model) {
    try {
      var category =
          categoryQuery
              .findByBusinessIdAndId(
                  applicationContext.getBusinessId(), updateOfferingDTO.getCategoryId())
              .orElseThrow(CategoryNotFoundException::new);
      if (updateOfferingDTO.getId() == null) {
        createOffering.execute(
            applicationContext.getBusinessId(),
            updateOfferingDTO.getCategoryId(),
            new CreateOfferingInput(
                category.getLanguage(),
                updateOfferingDTO.getName(),
                updateOfferingDTO.getDescription(),
                updateOfferingDTO.getPrice(),
                getImages()));
        model.addAttribute("successMessage", "Offering created successfully!");
      } else {
        updateOffering.execute(
            applicationContext.getBusinessId(),
            updateOfferingDTO.getOriginalCategoryId(),
            updateOfferingDTO.getId(),
            new UpdateOfferingInput(
                updateOfferingDTO.getCategoryId(),
                updateOfferingDTO.getName(),
                updateOfferingDTO.getDescription(),
                updateOfferingDTO.getPrice()));
        model.addAttribute("successMessage", "Offering updated successfully!");
      }
      return index(model);
    } catch (DomainException | ApplicationException | WebException e) {
      model.addAttribute("offering", updateOfferingDTO);
      model.addAttribute("categories", categoryQuery.findAll(applicationContext.getBusinessId()));
      model.addAttribute("errorMessage", e.getMessage());
      return "admin/business/offering/offering-form";
    }
  }

  private static List<String> getImages() {
    Random random = new Random();
    return List.of("vp-" + (random.nextInt(12) + 1) + ".png");
  }
}
