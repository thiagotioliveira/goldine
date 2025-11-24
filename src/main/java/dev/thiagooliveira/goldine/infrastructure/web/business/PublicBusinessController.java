package dev.thiagooliveira.goldine.infrastructure.web.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.thiagooliveira.goldine.application.usecase.GetBusiness;
import dev.thiagooliveira.goldine.infrastructure.web.business.dto.BusinessDTO;
import dev.thiagooliveira.goldine.infrastructure.web.exception.BusinessNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class PublicBusinessController {

  private final GetBusiness getBusiness;
  private final ObjectMapper objectMapper;

  public PublicBusinessController(GetBusiness getBusiness) {
    this.getBusiness = getBusiness;
    this.objectMapper = new ObjectMapper();
  }

  @RequestMapping("/{alias}")
  public String index(@PathVariable String alias, Model model) throws JsonProcessingException {
    var businessDto =
        new BusinessDTO(getBusiness.execute(alias).orElseThrow(BusinessNotFoundException::new));
    model.addAttribute("business", businessDto);
    model.addAttribute("businessJson", new ObjectMapper().writeValueAsString(businessDto));
    return "index";
  }
}
