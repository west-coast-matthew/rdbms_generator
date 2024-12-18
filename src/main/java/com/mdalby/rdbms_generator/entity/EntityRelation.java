package com.mdalby.rdbms_generator.entity;

import com.mdalby.rdbms_generator.types.EntityRelationTypes;

/**
 * EntityRelation.java
 * 
 * Models a relation between two entities.  
 * 
 * 
 */
public class EntityRelation {
	
	public EntityDefinition reference;
	
	// TODO: We are defaulting to a cascade approach, ideally we need to 
	// support alternate strategies.
	public boolean cascadeDelete = true;
	
	public String parentEntityName;
	public String childEntityName;
	public String parentIdColumn;
	public String childFkColumn;
	
	public EntityRelationTypes relationType;
	
}
