
package com.mdalby.rdbms_generator;

import java.util.ArrayList;

import com.mdalby.rdbms_generator.consumer.Consumer;
import com.mdalby.rdbms_generator.consumer.PostgreSQLConsumer;
import com.mdalby.rdbms_generator.entity.EntityDefinition;
import com.mdalby.rdbms_generator.generator.DefaultDSLGenerator;
import com.mdalby.rdbms_generator.generator.Generator;
import com.mdalby.rdbms_generator.publisher.LocalFileEntityPublisher;
import com.mdalby.rdbms_generator.publisher.Publisher;

/**
 * DSLGenerator.java
 * 
 * Main routine for reverse engineering a data from a source, converting 
 * it into an DSL (Domain specific language), and the publishing it to a final 
 * destination.
 * 
 */
public class DSLGenerator {
	
	public static Consumer getConsumer() {
		return new PostgreSQLConsumer();
	}
	
	public static Generator getGenerator() {
		return new DefaultDSLGenerator();
	}
	
	public static Publisher getPublisher() {
		return new LocalFileEntityPublisher();
	}
	
	public static void main(String[] args) {
		System.out.println("Generator: Initializing");
		
		try {
			
			Consumer consumer;
			
			// Reverse engineer existing source into entity representations
			consumer = DSLGenerator.getConsumer();
			ArrayList<EntityDefinition> entities = consumer.reverseDataSources();
			
			Generator generator = getGenerator();
			Publisher publisher = getPublisher();
			
			// Purge any previous generation efforts.
			publisher.initDirectory();
			
			// Process entities
			String entityDsl;
			for(EntityDefinition entity:entities){
				// Generate DSL from entities
				entityDsl = generator.generate(entity);
				
				// Persist the resulting DSL
				publisher.publishEntity(entity.entityName, entityDsl);
					
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Generator: Complete");
	}
}
