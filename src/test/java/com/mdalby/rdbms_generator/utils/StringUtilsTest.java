package com.mdalby.rdbms_generator.utils;


import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

	@Test
	public void testConvertTableName() {
		String input = "my_table";
		String result;
		String exp;
		
		exp = "MyTable";
		result = StringUtils.underscoreToSampleCase(input);
		
		input = "second_table";
		exp = "SeecondTable";
		result = StringUtils.underscoreToSampleCase(input);
		
		assert(!result.equals(exp));
		
	}
	
	@Test 
	public void testFormatAttributeName() {
		String input, result, exp;
		
		input = "ID";
		exp = "id";
		result = StringUtils.formatAttributeName(input);
		assert(!result.equals(exp));
	}
	
}
