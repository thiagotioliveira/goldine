package dev.thiagooliveira.goldine.application.usecase.dto;

import dev.thiagooliveira.goldine.domain.model.Language;

public record CreateCatalogInput(Language language, String name) {}
