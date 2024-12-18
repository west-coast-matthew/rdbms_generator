package com.mdalby.rdbms_generator.consumer;

import java.util.ArrayList;

import com.mdalby.rdbms_generator.entity.EntityDefinition;

/**
 * Represents a component responsible for reverse engineering data from an 
 * existing source into DSL format. 
 * 
 */
public interface Consumer {
	public ArrayList<EntityDefinition>reverseDataSources()throws Exception;
}
