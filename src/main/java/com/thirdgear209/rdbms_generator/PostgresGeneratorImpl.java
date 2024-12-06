package com.thirdgear209.rdbms_generator;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;

import com.thirdgear209.rdbms_generator.entity.AttributeDefinition;
import com.thirdgear209.rdbms_generator.entity.EntityDefinition;
import com.thirdgear209.rdbms_generator.utils.FileUtils;
import com.thirdgear209.rdbms_generator.utils.JsonUtils;
import com.thirdgear209.rdbms_generator.utils.StringUtils;


/**
 * PostgresGeneratorImpl.java
 * 
 * Postgress implementation of the DDL generator. 
 * 
 * Note:
 * 	We 'assume' that files will follow a naming convention using underscores to 
 * delimit words, this should be something that is configurable.
 * 
 */
public class PostgresGeneratorImpl implements Generator{

	String targetDir = "./source-generation";
	
	
	void parseEntity(String tableName, DatabaseMetaData meta)throws Exception {
	
		String entityName = StringUtils.underscoreToSampleCase(tableName);
		
		EntityDefinition entityRef = new EntityDefinition();
		entityRef.name = entityName;
		
		ResultSet columns = meta.getColumns(null, null, tableName, "%");
		
		 while (columns.next()) {
			String columnName = columns.getString("COLUMN_NAME").toLowerCase();
			AttributeDefinition attr = new AttributeDefinition();
			
			entityRef.attributes.add(attr);
            String columnType = columns.getString("TYPE_NAME");
            String columnRef = columnType.toLowerCase();
            
            if(columnRef=="serial") {
            	entityRef.stereoType = ColumnStereotypes.PK;
            }
            else if(columnName=="created_at" ) {
            	entityRef.stereoType = ColumnStereotypes.CREATE_TIMESTAMP;
            }
            else if( columnName=="updated_at") {
            	entityRef.stereoType = ColumnStereotypes.UPDATE_TIMESTAMP;
            }
            else {
            	entityRef.stereoType = ColumnStereotypes.DEFAULT;
            }
            System.out.println(columns);
            
            //
            // System.out.println(columns.getString("DATA_TYPE"));
            if( columns.getString("IS_NULLABLE").toLowerCase() == "yes" ) {
            	entityRef.isRequired = true;
            }
            
            
            //
            int columnTypeCode = Integer.parseInt(columns.getString("DATA_TYPE"));
            switch(columnTypeCode) {
            	case Types.CHAR:
            		entityRef.dataType = ColumnTypes.CHAR;
            		break;
            	case Types.BIT:
            		entityRef.dataType = ColumnTypes.BOOLEAN;
            		break;
            	case Types.VARCHAR:
            		entityRef.dataType = ColumnTypes.VARCHAR;
            		break;
            		
            	case Types.BINARY:
            		entityRef.dataType = ColumnTypes.BOOLEAN;
            		break;
            		
            	case Types.DATE:
            		entityRef.dataType = ColumnTypes.DATE;
            		break;
            		
            	case Types.NUMERIC:
            		entityRef.dataType = ColumnTypes.INTEGER;
            		break;
            	
            	case Types.DOUBLE:
            		entityRef.dataType = ColumnTypes.DECIMAL;
            		break;
            }
            
            
	            
	            // ... Access other column details like data type, nullability, etc.

	            System.out.println("Column: " + columnName + ", Type: " + columnType);

			 
		 }
		
		String entityJson = JsonUtils.entityToJson(entityRef);
		System.out.println(entityJson);
		FileUtils.writeFile(targetDir +"/"+ entityName, entityJson);
	}
	
	@Override
	public ArrayList<EntityDefinition> generate(String url, String userName, String password)throws Exception {
		
		Connection con = null;
		try {
			ArrayList entities = new ArrayList();
			
			//Ensure the build directory is present, an empty
			File directory = new File(targetDir);

	        if (directory.exists() && directory.isDirectory()) {
	            FileUtils.deleteDirectory(targetDir);
	        }
	        new File(targetDir).mkdirs();
			
			//Connect to database 
	        Class.forName("org.postgresql.Driver");
	        con = DriverManager.getConnection(url, userName, password);
	        
	        DatabaseMetaData meta = con.getMetaData();
	        try (ResultSet tables = meta.getTables(null, null, "%", new String[] { "TABLE" })) {
	            while (tables.next()) {
	                System.out.println(tables.getString("TABLE_NAME"));
	                parseEntity(tables.getString("TABLE_NAME"), meta);
	                
	                System.out.println();
	            }
	        }
	        
			
	        
			//Reverse engineer
			
			return entities;
		}
		finally {
			if(con!=null){
				con.close();
			};
		}
		
	}
	
}
