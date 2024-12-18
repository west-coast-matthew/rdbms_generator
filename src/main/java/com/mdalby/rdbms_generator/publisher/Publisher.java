package com.mdalby.rdbms_generator.publisher;

import com.mdalby.rdbms_generator.entity.EntityDefinition;

/**
 * Entities that are responsible for persisting the DSL model.
 */
public interface Publisher {
	
	public void publishEntity(String entityName, String entityJson)throws Exception;
	
	public void initDirectory()throws Exception;
	
}
