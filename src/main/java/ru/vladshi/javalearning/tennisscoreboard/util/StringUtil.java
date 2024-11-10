package ru.vladshi.javalearning.tennisscoreboard.util;

public class StringUtil {

    public static String capitalize(String string) {

        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public static String removeRedundantSpaces(String string) {
        if (string != null) {
            string = string.strip();
            string = string.replaceAll("\\s+", " ");
        }
        return string;
    }
}
