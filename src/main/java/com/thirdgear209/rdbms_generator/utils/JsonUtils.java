package com.thirdgear209.rdbms_generator.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thirdgear209.rdbms_generator.entity.EntityDefinition;

public class JsonUtils {

	public static String entityToJson(EntityDefinition entity) throws JsonProcessingException {
	    ObjectMapper objectMapper = new ObjectMapper();
	    return objectMapper.writeValueAsString(entity);
	}
	
}
