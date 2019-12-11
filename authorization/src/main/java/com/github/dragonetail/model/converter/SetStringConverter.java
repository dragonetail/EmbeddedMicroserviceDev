package com.github.dragonetail.model.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Set字段DB转换，序列化
 *
 * @author sunyx
 */
public class SetStringConverter implements AttributeConverter<Set<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<String> dataSet) {
        return String.join(" ", dataSet);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dataSetString) {
        return new HashSet<String>(Arrays.asList(dataSetString.split(" ")));
    }
}