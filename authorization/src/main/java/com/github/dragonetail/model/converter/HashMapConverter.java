package com.github.dragonetail.model.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;

/**
 * HashMap字段DB转换，序列化
 *
 * @author sunyx
 */
@Slf4j
public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, Object> dataMap) {

        String dataMapJson = null;
        try {
            dataMapJson = objectMapper.writeValueAsString(dataMap);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return dataMapJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String dataMapJson) {

        Map<String, Object> dataMap = null;
        try {
            dataMap = objectMapper.readValue(dataMapJson, Map.class);
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return dataMap;
    }

}