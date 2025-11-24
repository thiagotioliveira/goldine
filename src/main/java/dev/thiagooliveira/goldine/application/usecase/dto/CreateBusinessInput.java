package dev.thiagooliveira.goldine.application.usecase.dto;

import dev.thiagooliveira.goldine.domain.model.Language;
import java.util.Set;

public record CreateBusinessInput(String name, Set<Language> supportedLanguages) {}
