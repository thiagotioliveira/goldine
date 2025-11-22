package dev.thiagooliveira.goldine.application.usecase.command.dto;

import dev.thiagooliveira.goldine.domain.model.Language;
import java.util.Set;

public record CreateBusinessInput(String name, Set<Language> supportedLanguages) {}
