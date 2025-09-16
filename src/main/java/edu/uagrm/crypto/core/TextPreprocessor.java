package edu.uagrm.crypto.core;

import java.text.Normalizer;

public class TextPreprocessor {
    
    public static String normalize(String text, TextOptions options, Alphabet alphabet) {
        if (text == null) return "";
        
        String result = text;
        
        if (!options.isPreserveAccents()) {
            result = removeAccents(result);
        }
        
        if (!options.isPreserveCase()) {
            result = result.toUpperCase();
        }
        
        if (!options.isPreserveSpaces()) {
            result = result.replaceAll("\\s+", "");
        }
        
        if (!options.isPreservePunctuation()) {
            result = result.replaceAll("[^\\p{L}\\p{N}\\s]", "");
        }
        
        StringBuilder filtered = new StringBuilder();
        for (char c : result.toCharArray()) {
            if (alphabet.contains(c) ||
                (options.isPreserveSpaces() && Character.isWhitespace(c)) ||
                (options.isPreservePunctuation() && !Character.isLetterOrDigit(c) && !Character.isWhitespace(c))) {
                filtered.append(c);
            }
        }

        return filtered.toString();
    }
    
    private static String removeAccents(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}