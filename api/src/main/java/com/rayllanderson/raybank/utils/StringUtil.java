package com.rayllanderson.raybank.utils;

public class StringUtil {

    /**
     *
     * @return true if they are not equals
     */
    public static boolean notMatches(String str1, String str2){
        if (str1 == null || str2 == null) return false;
        return !(str1.equals(str2));
    }

    public static boolean isEmpty(String field){
        return field != null && (field.isEmpty() || field.trim().isEmpty());
    }
}
