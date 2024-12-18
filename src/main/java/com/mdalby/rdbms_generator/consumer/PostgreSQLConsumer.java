package com.mdalby.rdbms_generator.consumer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import com.mdalby.rdbms_generator.entity.AttributeDefinition;
import com.mdalby.rdbms_generator.entity.EntityDefinition;
import com.mdalby.rdbms_generator.types.ColumnStereotypes;
import com.mdalby.rdbms_generator.types.ColumnTypes;
import com.mdalby.rdbms_generator.utils.StringUtils;

public class PostgreSQLConsumer implements Consumer{
	
	// TODO: Externalize... 
		String url = "jdbc:postgresql://localhost:5432/example";
		String userName = "postgres";
		String password = "sql";
		
	Connection getConnection()throws Exception {
		// TODO: externalize db dependency
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(url, userName, password);
        return con;
	}
	
	/**
	 * Translate database meta data into object representations. Meta information will 
	 * be translated into entity and attribute definitions.
	 * 
	 * @param tables
	 * @return
	 */
	public ArrayList<EntityDefinition>parseEntities(DatabaseMetaData meta)throws Exception{
		ArrayList<EntityDefinition> entities = new ArrayList();
		
		String tableName, columnName;
		ResultSet columns;
		ResultSet tables = meta.getTables(null, null, "%", new String[] { "TABLE" });
		EntityDefinition entity;
		AttributeDefinition attr;
		while (tables.next()) {
    		tableName = tables.getString("TABLE_NAME");
    		entity = new EntityDefinition(StringUtils.underscoreToSampleCase(tableName), tableName);
    		entities.add(entity);	        		
    	
    		columns = meta.getColumns(null, null, tableName, "%");
    		while (columns.next()) {
    			columnName = columns.getString("COLUMN_NAME").toLowerCase();
				attr = new AttributeDefinition(StringUtils.formatAttributeName(columnName), columnName);
				entity.addAttribute(attr);
    		}
		}
		
		return entities;
	}
	
	/**
	 * Extract column level details into the DSL model, i.e. column type and constraints.
	 * 
	 * 
	 * @param entityRefs
	 * @param meta
	 * @throws Exception
	 */
	public void setColumnAttributes(ArrayList<EntityDefinition>entityRefs, DatabaseMetaData meta)throws Exception{
		ResultSet columns;
		String columnName;
		AttributeDefinition attr;
		int columnType;
		for(EntityDefinition entity: entityRefs) {
			columns = meta.getColumns(null, null, entity.tableName, "%");
			while (columns.next()) {
				
				columnType = columns.getInt("DATA_TYPE");
				columnName = StringUtils.formatAttributeName(columns.getString("COLUMN_NAME"));
				
				attr = entity.getAttributeByName(columnName);
				
				switch(columnType) {
	            	case Types.CHAR:
	            	case Types.VARCHAR:
	            		attr.dataType = ColumnTypes.COLUMN_TYPE_STRING;
	            		break;		
	            	case Types.BIT:
	            	case Types.BINARY:
	            		attr.dataType = ColumnTypes.COLUMN_TYPE_BOOLEAN;
	            		break;
	            	case Types.DATE:
	            		attr.dataType = ColumnTypes.COLUMN_TYPE_DATE;
	            		break;
	            	case Types.INTEGER:
	            	case Types.NUMERIC:
	            		attr.dataType = ColumnTypes.COLUMN_TYPE_INTEGER;
	            		break;
	            	case Types.FLOAT:
	            	case Types.DOUBLE:
	            		attr.dataType = ColumnTypes.COLUMN_TYPE_DECIMAL;
	            		break;
				}
				
				if( columns.getString("IS_NULLABLE").toLowerCase() == "yes" ) {
	            	attr.isRequired = true;
	            }
				
			}
		}
	}
	
	void assignRelations(ArrayList<EntityDefinition>entityRefs, DatabaseMetaData meta) throws Exception{
		ResultSet rs;
		
		// Create a map to store entity refs by their associated table
		HashMap<String, EntityDefinition> entityRefMap = new HashMap();
		for(EntityDefinition entity:entityRefs) {
			entityRefMap.put(entity.tableName, entity);
		}
		
		// Iterate through each defined entity, querying exported FKs from each associated
		// table reference. 
		String fkTableName;
	    String fkColumnName;
	    EntityDefinition child;
		for(EntityDefinition entity:entityRefs) {
			rs = meta.getExportedKeys(null, null, entity.tableName);
			while(rs.next()) {
				fkTableName = rs.getString("FKTABLE_NAME"); 
			    fkColumnName = rs.getString("FKCOLUMN_NAME"); 
			    
			    child = entityRefMap.get(fkTableName);
			    entity.addChildAssociation(child, fkColumnName);
			    
			    child.addParentAssociation(entity, fkColumnName);
			}
		}
		
	}
	
	/**
	 * Set stereotype information for entities.
	 * 
	 * @param entityRefs
	 */
	public void setColumnStereoTypes(ArrayList<EntityDefinition>entityRefs, DatabaseMetaData meta)throws Exception{
		
		ResultSet columns;
		String columnName;
		AttributeDefinition attr;
		String columnType;
		int columnTypeCode;
		for(EntityDefinition entity: entityRefs) {
			columns = meta.getColumns(null, null, entity.tableName, "%");
			while (columns.next()) {
				
				columnName = columns.getString("COLUMN_NAME");
				attr = entity.getAttributeByName(StringUtils.formatAttributeName(columns.getString("COLUMN_NAME")));
				
				switch(columnName) {
					case "id":
						attr.stereoType = ColumnStereotypes.PK;
						break;
					case "created_at":
						attr.stereoType = ColumnStereotypes.CREATE_TIMESTAMP;
						break;
					case "updated_at":
						attr.stereoType = ColumnStereotypes.UPDATE_TIMESTAMP;
						break;
					default:
						attr.stereoType = ColumnStereotypes.DEFAULT;
						break;
				}
				
			}
		}
		
	}
	
	@Override
	public ArrayList<EntityDefinition> reverseDataSources() throws Exception{
		Connection con = null;
		con = getConnection();
		
		try {
			DatabaseMetaData meta = con.getMetaData();
			
			ResultSet tables = meta.getTables(null, null, "%", new String[] { "TABLE" });
	        
			ArrayList<EntityDefinition> entities = parseEntities(meta);
			
			// Set entity attributes stereotypes		
	        setColumnStereoTypes(entities, meta);
	        		
        	// Populate entities with attribute types
	        setColumnAttributes(entities, meta);
	        
	        // Finally wire in relations.
	        assignRelations(entities, meta);
	        
	        return entities;
		}
		finally {
			if(con!=null) {
				try { con.close(); }catch(Exception e) {}
			}
		}
			
	}
	
}
