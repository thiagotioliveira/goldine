package dev.thiagooliveira.goldine.application.usecase.command.dto;

import dev.thiagooliveira.goldine.domain.model.Language;

public record CreateCatalogInput(Language language, String name) {}
