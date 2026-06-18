package io.github.fedverdev.docxprocessor.core.config;


import io.github.fedverdev.docxprocessor.core.enums.NormalizerType;
import lombok.Data;

import java.nio.file.Path;

@Data
public final class ProcessorConfig {
    private final Path inputDirectory;
    private final Path outputDirectory;
    private final NormalizerType normalizerType;
    private final GeneratorConfig generatorConfig;
}