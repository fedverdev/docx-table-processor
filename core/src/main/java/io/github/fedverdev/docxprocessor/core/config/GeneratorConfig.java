package io.github.fedverdev.docxprocessor.core.config;

import io.github.fedverdev.docxprocessor.core.enums.Language;
import lombok.Data;

@Data
public final class GeneratorConfig {
    private final Language language;
}