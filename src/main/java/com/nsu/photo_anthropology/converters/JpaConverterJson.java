package com.nsu.photo_anthropology.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import org.json.simple.JSONArray;

import javax.persistence.AttributeConverter;

public class JpaConverterJson implements AttributeConverter<JSONArray, String> {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(JSONArray meta) {
        try {
            return objectMapper.writeValueAsString(meta);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new PhotoAnthropologyRuntimeException("json error", e);
        }
    }

    @Override
    public JSONArray convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, JSONArray.class);
        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("json error", e);
        }
    }
}