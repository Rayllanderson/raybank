package com.rayllanderson.raybank.utils;

public class StringUtil {

    /**
     *
     * @return true if they are not equals
     */
    public static boolean notMatches(String str1, String str2){
        if (str1 == null || str2 == null) return true;
        return !(str1.equals(str2));
    }

    public static boolean isEmpty(String field){
        return field != null && (field.isEmpty() || field.trim().isEmpty());
    }

    public static String capitalize(String line) {
        char[] chars = line.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }
}
