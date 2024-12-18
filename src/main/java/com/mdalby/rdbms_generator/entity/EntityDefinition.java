package com.mdalby.rdbms_generator.entity;

import java.util.ArrayList;
import java.util.List;

import com.mdalby.rdbms_generator.types.EntityRelationTypes;

/**
 * EntityDefinition.java
 * 
 * Represents an entity.
 * 
 */
public class EntityDefinition {
	
	public String entityName;
	public String tableName;
	public List<EntityRelation> childCollections = new ArrayList();
	public EntityRelation parent;
	
	public EntityDefinition(String entityName, String tableName) {
		this.entityName = entityName;
		this.tableName = tableName;
	}
	
	public List<AttributeDefinition> attributes = new ArrayList();
	
	public AttributeDefinition getAttributeByName(String name){
		
		for(AttributeDefinition att:attributes ) {
			if( att.name.equals(name) ) {
				return att;
			}
		}
		
		return null;
	}
	
	public AttributeDefinition getAttributeByColumnName(String columnName){
		
		for(AttributeDefinition att:attributes ) {
			if( att.columnName.equals(columnName) ) {
				return att;
			}
		}
		
		return null;
	}
	
	public void addAttribute(AttributeDefinition attr) {
		this.attributes.add(attr);
	}
	
	/**
	 * Establish a parent child relationship. This operation will not 
	 * only add the referenced entity as a child dependency, it will also 
	 * update the referenced child entity so that it references the parent as 
	 * well. Call this on the 'parent' in order to associate a child collection. 
	 *  
	 * @param childRef EntityDefintion object that represents what will be 
	 * in the child collection.  
	 * 
	 * @param fkFieldRef The name of the field in the child table that will 
	 * reference to the parent table.
	 */
	public void addChildAssociation(EntityDefinition childRef, String fkFieldRef) {
		
		EntityRelation relationDef = new EntityRelation();
		relationDef.parentEntityName = this.entityName;
		relationDef.childEntityName = childRef.entityName;
		relationDef.parentIdColumn = "id";
		relationDef.childFkColumn = fkFieldRef;
		relationDef.relationType = EntityRelationTypes.MANY_TO_ONE;
		this.childCollections.add(relationDef);
	}

	/**
	 * Establish a reference to a parent when the current entity assumes the role
	 * of 'many' in a one to many relation.
	 * 
	 * Call this on the child to set a reference to the parent.
	 */
	public void addParentAssociation(EntityDefinition parent, String fkFieldRef) {
		EntityRelation relationDef = new EntityRelation();
		relationDef.parentEntityName = parent.entityName;
		relationDef.childEntityName = this.entityName;
		relationDef.parentIdColumn = "id";
		relationDef.childFkColumn = fkFieldRef;
		relationDef.relationType = EntityRelationTypes.MANY_TO_ONE;
		this.childCollections.add(relationDef);
	}
	
	
	
	/**
	 * 
	 */
	public void addEntityAssocation() {
		
	}
}
