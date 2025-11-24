package dev.thiagooliveira.goldine.application.usecase.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateOfferingInput(
    UUID categoryId, String name, String description, BigDecimal price) {}
