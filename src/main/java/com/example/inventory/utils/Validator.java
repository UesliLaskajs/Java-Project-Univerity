package com.example.inventory.utils;

import java.util.regex.Pattern;

/**
 * Utility class for input validation using regular expressions.
 */
public class Validator {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern PRICE_PATTERN  = Pattern.compile("\\d+(\\.\\d{1,2})?");

    public static boolean isValidQuantity(String input) {
        return NUMBER_PATTERN.matcher(input).matches();
    }

    public static boolean isValidPrice(String input) {
        return PRICE_PATTERN.matcher(input).matches();
    }
}
