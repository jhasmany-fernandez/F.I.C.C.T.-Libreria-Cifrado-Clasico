package edu.uagrm.crypto.core;

import java.util.HashSet;
import java.util.Set;

public class Keys {
    
    public static boolean isValidCaesarKey(String key) {
        try {
            int shift = Integer.parseInt(key);
            return shift >= 0 && shift <= 25;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidVigenereKey(String key, Alphabet alphabet) {
        if (key == null || key.isEmpty()) return false;
        
        String normalizedKey = key.toUpperCase();
        for (char c : normalizedKey.toCharArray()) {
            if (!alphabet.contains(c)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValidSubstitutionKey(String key, Alphabet alphabet) {
        if (key == null || key.length() != alphabet.size()) {
            return false;
        }
        
        Set<Character> usedChars = new HashSet<>();
        String normalizedKey = key.toUpperCase();
        
        for (char c : normalizedKey.toCharArray()) {
            if (!alphabet.contains(c) || usedChars.contains(c)) {
                return false;
            }
            usedChars.add(c);
        }
        
        return usedChars.size() == alphabet.size();
    }
    
    public static String generateCaesarKey(int shift) {
        return String.valueOf(shift % 26);
    }
    
    public static String normalizeKey(String key, Alphabet alphabet) {
        if (key == null) return "";
        
        StringBuilder normalized = new StringBuilder();
        String upperKey = key.toUpperCase();
        
        for (char c : upperKey.toCharArray()) {
            if (alphabet.contains(c)) {
                normalized.append(c);
            }
        }
        
        return normalized.toString();
    }
}