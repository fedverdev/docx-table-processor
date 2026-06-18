package io.github.fedverdev.docxprocessor.core.normilizer;

import io.github.fedverdev.docxprocessor.core.exceptions.TableProcessingException;
import io.github.fedverdev.docxprocessor.core.model.Table;

import java.util.List;

public interface TableNormalizer {
    Table normalize(List<Table> fragments) throws TableProcessingException;
}