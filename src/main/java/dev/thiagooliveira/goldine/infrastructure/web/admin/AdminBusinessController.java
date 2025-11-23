package dev.thiagooliveira.goldine.infrastructure.web.admin;

import dev.thiagooliveira.goldine.application.usecase.command.GetBusiness;
import dev.thiagooliveira.goldine.application.usecase.command.UpdateBusiness;
import dev.thiagooliveira.goldine.application.usecase.command.dto.UpdateBusinessInput;
import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.domain.model.SocialLink;
import dev.thiagooliveira.goldine.domain.model.SocialLinkType;
import dev.thiagooliveira.goldine.infrastructure.config.context.ApplicationContext;
import dev.thiagooliveira.goldine.infrastructure.persistence.query.BusinessQuery;
import dev.thiagooliveira.goldine.infrastructure.web.admin.dto.EditBusinessDTO;
import dev.thiagooliveira.goldine.infrastructure.web.exception.BusinessNotFoundException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/business")
public class AdminBusinessController {

  private final ApplicationContext applicationContext;
  private final BusinessQuery businessQuery;
  private final GetBusiness getBusiness;
  private final UpdateBusiness updateBusiness;

  public AdminBusinessController(
      ApplicationContext applicationContext,
      BusinessQuery businessQuery,
      GetBusiness getBusiness,
      UpdateBusiness updateBusiness) {
    this.applicationContext = applicationContext;
    this.businessQuery = businessQuery;
    this.getBusiness = getBusiness;
    this.updateBusiness = updateBusiness;
  }

  @GetMapping()
  public String index(Model model) {
    var business =
        businessQuery
            .findById(applicationContext.getBusinessId())
            .orElseThrow(BusinessNotFoundException::new);
    model.addAttribute("business", new EditBusinessDTO(business));
    return "admin/business/business-form";
  }

  @PostMapping("/save")
  public String save(@ModelAttribute EditBusinessDTO editBusinessDTO, Model model) {
    var business =
        updateBusiness.execute(
            applicationContext.getBusinessId(),
            new UpdateBusinessInput(
                editBusinessDTO.getName(),
                editBusinessDTO.getAddress(),
                editBusinessDTO.getSupportedLanguages().stream()
                    .map(Language::valueOf)
                    .collect(Collectors.toSet()),
                editBusinessDTO.getSocialLinks().stream()
                    .map(s -> SocialLink.create(SocialLinkType.valueOf(s.getType()), s.getUrl()))
                    .collect(Collectors.toSet())));
    model.addAttribute("successMessage", "Business updated successfully!");
    return index(model);
  }
}
