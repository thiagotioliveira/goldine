package dev.thiagooliveira.goldine.application.usecase.dto;

import java.util.UUID;

public record UpdateCategoryInput(UUID catalogId, String name) {}
