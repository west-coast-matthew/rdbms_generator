package com.mdalby.rdbms_generator.generator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdalby.rdbms_generator.entity.EntityDefinition;

public class DefaultDSLGenerator implements Generator{

	@Override
	public String generate(EntityDefinition entity)throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity);
	}
	
}
