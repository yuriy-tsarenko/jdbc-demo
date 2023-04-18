package com.goit.converter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RowsConverter {

    public static String convertToString(List<Map<String, Object>> rows) {
        if (rows.isEmpty()) {
            return "";
        }
        Set<String> columnNames = rows.get(0).keySet();
        String headerRow = String.join("|", columnNames);
        List<String> valueRows = rows.stream()
                .map(row -> row.values().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining("|")))
                .toList();
        StringBuilder builder = new StringBuilder();
        builder
                .append(headerRow)
                .append("\n");
        valueRows.forEach(row -> builder
                .append(row)
                .append("\n"));
        return builder.toString();
    }

}
