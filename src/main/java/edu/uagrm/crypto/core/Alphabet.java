package edu.uagrm.crypto.core;

public class Alphabet {
    
    public static final String SPANISH = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    public static final String ENGLISH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";
    
    private String chars;
    
    public Alphabet(String chars) {
        this.chars = chars.toUpperCase();
    }
    
    public static Alphabet getSpanish() {
        return new Alphabet(SPANISH);
    }
    
    public static Alphabet getEnglish() {
        return new Alphabet(ENGLISH);
    }
    
    public String getChars() {
        return chars;
    }
    
    public int size() {
        return chars.length();
    }
    
    public int indexOf(char c) {
        return chars.indexOf(Character.toUpperCase(c));
    }
    
    public char charAt(int index) {
        return chars.charAt(index % chars.length());
    }
    
    public boolean contains(char c) {
        return indexOf(c) != -1;
    }
}