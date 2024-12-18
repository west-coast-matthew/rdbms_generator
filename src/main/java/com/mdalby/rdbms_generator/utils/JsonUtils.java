package com.mdalby.rdbms_generator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mdalby.rdbms_generator.entity.EntityDefinition;

public class JsonUtils {

	public static String entityToJson(EntityDefinition entity) throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	    
	    return objectMapper.writeValueAsString(entity);
	}
	
}
