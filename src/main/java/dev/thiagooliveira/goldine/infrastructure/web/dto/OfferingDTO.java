package dev.thiagooliveira.goldine.infrastructure.web.dto;

import static dev.thiagooliveira.goldine.infrastructure.util.Constants.*;

import dev.thiagooliveira.goldine.domain.model.Offering;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OfferingDTO {
  private final UUID id;
  private final String name;
  private final String description;
  private final String price;
  private final List<String> images;

  public OfferingDTO(Offering offering) {
    this.id = offering.getId();
    this.name = offering.getName();
    this.description = offering.getDescription();
    this.price = offering.getPrice().toString() + CURRENCY;
    this.images =
        offering.getImages().stream()
            .map(s -> String.format(PREFIX_IMAGE_URL, s))
            .collect(Collectors.toList());
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getPrice() {
    return price;
  }

  public List<String> getImages() {
    if (images == null || images.isEmpty()) {
      return Collections.emptyList();
    }

    List<String> result = new ArrayList<>();
    for (int i = 0; i < 3; i++) { // TODO
      result.add(images.get(i % images.size()));
    }
    return result;
  }
}
