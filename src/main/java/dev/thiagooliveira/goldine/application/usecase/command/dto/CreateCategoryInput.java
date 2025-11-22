package dev.thiagooliveira.goldine.application.usecase.command.dto;

import dev.thiagooliveira.goldine.domain.model.Language;
import java.util.UUID;

public record CreateCategoryInput(UUID catalogId, Language language, String name) {}
