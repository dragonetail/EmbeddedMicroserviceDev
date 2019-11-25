package com.baeldung.model.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SetStringConverter implements AttributeConverter<Set<String>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<String> dataSet) {
        return String.join(",", dataSet);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dataSetString) {
        return new HashSet<String>(Arrays.asList(dataSetString.split(",")));
    }
}