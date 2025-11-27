package br.com.academiadev.infrastructure.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class GenericCsvExporter {

    public static String exportToCsv(List<?> dataList, List<String> columns) {
        if (dataList == null || dataList.isEmpty()) {
            return "No data to export.";
        }

        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append(String.join(";", columns)).append("\n");

        for (Object object : dataList) {
            List<String> rowValues = columns.stream()
                .map(colName -> getFieldValue(object, colName))
                .collect(Collectors.toList());

            csvBuilder.append(String.join(";", rowValues)).append("\n");
        }

        return csvBuilder.toString();
    }

    private static String getFieldValue(Object object, String fieldName) {
        try {

            Field field = getFieldInClassHierarchy(object.getClass(), fieldName);
            field.setAccessible(true);
            Object value = field.get(object);
            return value != null ? value.toString() : "";
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return "ERROR";
        }
    }

    private static Field getFieldInClassHierarchy(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }
}
