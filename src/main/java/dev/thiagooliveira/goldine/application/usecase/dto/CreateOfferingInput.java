package dev.thiagooliveira.goldine.application.usecase.dto;

import dev.thiagooliveira.goldine.domain.model.Language;
import java.math.BigDecimal;
import java.util.List;

public record CreateOfferingInput(
    Language language, String name, String description, BigDecimal price, List<String> images) {}
