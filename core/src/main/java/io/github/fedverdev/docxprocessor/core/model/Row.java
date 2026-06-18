package io.github.fedverdev.docxprocessor.core.model;

import lombok.Data;

import java.util.List;

@Data
public final class Row {
    private final List<Cell> cells;
}