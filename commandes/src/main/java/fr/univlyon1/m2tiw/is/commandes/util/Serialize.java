package fr.univlyon1.m2tiw.is.commandes.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serialize {

    private Serialize() {
        // Hide public constructor, sonarlint smell S1118
    }

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static Object toObject(String json, Class<?> c) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, c);
    }

}
