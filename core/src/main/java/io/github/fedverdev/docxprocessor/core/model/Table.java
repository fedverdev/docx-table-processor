package io.github.fedverdev.docxprocessor.core.model;

import lombok.Data;

import java.util.List;

@Data
public final class Table {
    private final List<Row> rows;
    private final List<String> headers;
}