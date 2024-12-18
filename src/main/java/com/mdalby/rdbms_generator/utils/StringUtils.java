package com.mdalby.rdbms_generator.utils;

public class StringUtils {
	
	public static String underscoreToSampleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : input.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                } else {
                    result.append(c);
                }
                capitalizeNext = false;
            }
        }

        return result.toString();
    }
	
	public static String formatAttributeName(String name) {
		String ref = underscoreToSampleCase(name.toLowerCase());
		return ref.substring(0, 1).toLowerCase() + ref.substring(1);
	}
}
