package com.mdalby.rdbms_generator.publisher;

import com.mdalby.rdbms_generator.entity.EntityDefinition;
import com.mdalby.rdbms_generator.utils.FileUtils;
import com.mdalby.rdbms_generator.utils.JsonUtils;

/**
 * DSL publisher which persists entities into the local file 
 * system in JSON format. 
 * 
 */
public class LocalFileEntityPublisher implements Publisher{
	
	String targetDir = "./source-generation";
	
	public void publishEntity(String entityName, String entityJson)throws Exception{
		FileUtils.writeFile(targetDir +"/"+ entityName +".json", entityJson);
	}
	
	public void initDirectory()throws Exception{
		FileUtils.initDirectory(targetDir);
	}
	
}
