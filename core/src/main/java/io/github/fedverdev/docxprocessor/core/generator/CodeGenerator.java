package io.github.fedverdev.docxprocessor.core.generator;

import io.github.fedverdev.docxprocessor.core.config.GeneratorConfig;
import io.github.fedverdev.docxprocessor.core.exceptions.TableProcessingException;
import io.github.fedverdev.docxprocessor.core.model.Table;

public interface CodeGenerator {
    String generateJson(Table table, GeneratorConfig config) throws TableProcessingException;
    String generatePojo(Table table, GeneratorConfig config) throws TableProcessingException;
}