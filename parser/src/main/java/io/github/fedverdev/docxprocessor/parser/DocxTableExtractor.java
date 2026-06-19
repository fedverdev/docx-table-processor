package io.github.fedverdev.docxprocessor.parser;

import io.github.fedverdev.docxprocessor.core.exceptions.TableProcessingException;
import io.github.fedverdev.docxprocessor.core.extractor.TableExtractor;
import io.github.fedverdev.docxprocessor.core.model.Cell;
import io.github.fedverdev.docxprocessor.core.model.Row;
import io.github.fedverdev.docxprocessor.core.model.Table;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DocxTableExtractor implements TableExtractor {
    @Override
    public List<Table> extract(Path documentPath) throws TableProcessingException {
        try {
            List<Table> tables = new ArrayList<>();
            try (XWPFDocument document = new XWPFDocument(Files.newInputStream(documentPath))) {
                List<XWPFTable> xwpfTables = document.getTables();
                for (XWPFTable xwpfTable : xwpfTables) {
                    Table table = convertXWPFTableToTable(xwpfTable);
                    tables.add(table);
                }
            }
            return tables;
        } catch (IOException e) {
            throw new TableProcessingException("Failed to extract tables from DOCX document: " + documentPath, e);
        }
    }

    private Table convertXWPFTableToTable(XWPFTable xwpfTable) {
        List<Row> rows = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        List<XWPFTableRow> xwpfRows = xwpfTable.getRows();

        for (int rowIndex = 0; rowIndex < xwpfRows.size(); rowIndex++) {
            XWPFTableRow xwpfRow = xwpfRows.get(rowIndex);
            Row row = convertXWPFRowToRow(xwpfRow);
            rows.add(row);

            // Используем первую строку как заголовки
            if (rowIndex == 0) {
                headers = extractHeadersFromRow(row);
            }
        }

        return new Table(rows, headers);
    }

    private Row convertXWPFRowToRow(XWPFTableRow xwpfRow) {
        List<Cell> cells = new ArrayList<>();
        List<XWPFTableCell> xwpfCells = xwpfRow.getTableCells();

        for (XWPFTableCell xwpfCell : xwpfCells) {
            Cell cell = convertXWPFCellToCell(xwpfCell);
            cells.add(cell);
        }

        return new Row(cells);
    }

    private Cell convertXWPFCellToCell(XWPFTableCell xwpfCell) {
        String text = extractTextFromCell(xwpfCell);
        int rowSpan = getRowSpan(xwpfCell);
        int colSpan = getColSpan(xwpfCell);

        return new Cell(text, rowSpan, colSpan);
    }

    private String extractTextFromCell(XWPFTableCell xwpfCell) {
        StringBuilder cellText = new StringBuilder();
        xwpfCell.getParagraphs().forEach(paragraph -> {
            if (cellText.length() > 0) {
                cellText.append("\n");
            }
            cellText.append(paragraph.getText());
        });
        return cellText.toString().trim();
    }

    private int getRowSpan(XWPFTableCell xwpfCell) {
        // Apache POI не предоставляет простой API для rowSpan в XWPF
        // Для полной поддержки потребуется работа с CTTc напрямую
        return 1;
    }

    private int getColSpan(XWPFTableCell xwpfCell) {
        try {
            long gridSpan = xwpfCell.getCTTc().getTcPr().getGridSpan().getVal().longValue();
            return (int) gridSpan;
        } catch (Exception e) {
            return 1;
        }
    }

    private List<String> extractHeadersFromRow(Row row) {
        return row.getCells().stream()
                .map(Cell::getText)
                .toList();
    }
}
