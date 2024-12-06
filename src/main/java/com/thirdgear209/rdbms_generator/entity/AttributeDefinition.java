package com.thirdgear209.rdbms_generator.entity;

import com.thirdgear209.rdbms_generator.ColumnStereotypes;
import com.thirdgear209.rdbms_generator.ColumnTypes;

public class AttributeDefinition {
	
	public ColumnTypes dataType;
	public boolean required;
	public String defaultValue;
	public ColumnStereotypes stereoType;
	public boolean isRequired;
	
}
