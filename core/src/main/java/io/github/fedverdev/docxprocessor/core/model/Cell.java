package io.github.fedverdev.docxprocessor.core.model;

import lombok.Data;

@Data
public final class Cell {
    private final String text;
    private final int rowSpan;
    private final int colSpan;

}