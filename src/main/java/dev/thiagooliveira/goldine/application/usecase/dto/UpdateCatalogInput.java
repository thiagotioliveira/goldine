package dev.thiagooliveira.goldine.application.usecase.dto;

import dev.thiagooliveira.goldine.domain.model.Language;

public record UpdateCatalogInput(String name, Language language) {}
