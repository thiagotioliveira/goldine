package dev.thiagooliveira.goldine.application.usecase.command.dto;

import dev.thiagooliveira.goldine.domain.model.Language;
import dev.thiagooliveira.goldine.domain.model.SocialLink;
import java.util.Set;

public record UpdateBusinessInput(
    String name, String address, Set<Language> supportedLanguages, Set<SocialLink> socialLinks) {}
