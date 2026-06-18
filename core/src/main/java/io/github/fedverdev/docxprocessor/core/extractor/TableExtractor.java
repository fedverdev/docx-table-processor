package io.github.fedverdev.docxprocessor.core.extractor;

import io.github.fedverdev.docxprocessor.core.exceptions.TableProcessingException;
import io.github.fedverdev.docxprocessor.core.model.Table;

import java.nio.file.Path;
import java.util.List;

public interface TableExtractor {
    List<Table> extract(Path documentPath) throws TableProcessingException;
}