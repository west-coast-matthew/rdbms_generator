package com.mdalby.rdbms_generator.generator;

import com.mdalby.rdbms_generator.entity.EntityDefinition;

/**
 * DSLGenerator.java
 * 
 * Responsible for reverse engineering an schema into an DSL or domain specific language.
 * Once the schema is translated into this parsable format, then as a next step
 * we can generate source code from it.
 *
 * This interface abstracts the details from any specifics related to the actual RDBMs
 * implementation.	
 * 
 * Just to note, there is an assumption here that tables are named using a certain convention, 
 * i.e. 'entity_name' so underscore used to separate the entity name tokens, and 
 * the entity is referenced in on plural form. Definately a point where we can instructment 
 * a more flexible approach in the future.
 * 
 */
public interface Generator {
	public String generate(EntityDefinition entity)throws Exception; 
}
