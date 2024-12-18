package com.mdalby.rdbms_generator.entity;

import com.mdalby.rdbms_generator.types.*;
import com.mdalby.rdbms_generator.types.ColumnStereotypes;

/**
 * AttributeDefinition.java
 * 
 */
public class AttributeDefinition {

	public static final String COLUMN_TYPE_STRING = "STRING";
	public static final String COLUMN_TYPE_INTEGER = "INTEGER";
	public static final String COLUMN_TYPE_PK = "PK";
	public static final String COLUMN_TYPE_BOOLEAN = "BOOLEAN";
	public static final String COLUMN_TYPE_DECIMAL = "DECIMAL";
	public static final String COLUMN_TYPE_DATE = "DATE";
	
	public String name;
	public String columnName;
	public ColumnTypes dataType;
	public String defaultValue;
	public ColumnStereotypes stereoType;
	public boolean isRequired;
	
	public AttributeDefinition(String name, String columnName) {
		this.name = name;
		this.columnName = columnName;
	}
	
	public String toString() {
		return "AttributeDefinition: name=>"+ name;
	}
	
}
