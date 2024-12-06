package com.thirdgear209.rdbms_generator;

import java.util.ArrayList;

import com.thirdgear209.rdbms_generator.entity.EntityDefinition;


public interface Generator {

	public ArrayList<EntityDefinition>generate(String url, String userName, String password) throws Exception;
}
