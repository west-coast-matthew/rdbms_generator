package com.thirdgear209.rdbms_generator;

public class RdmsReverseGenerator {
	
	public static void main(String[] args) {
		System.out.println("Generator: Initializing");
		
		try {
			
			// TODO: Accept command line argument for RDBMS specific generator. 
			Generator generator = new PostgresGeneratorImpl();
			
			// TODO: 
			String url = "jdbc:postgresql://localhost:5432/example";
			String user = "postgres";
			String pass = "sql";
			generator.generate(url, user, pass);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		System.out.println("Generator: Complete");
	}
}
