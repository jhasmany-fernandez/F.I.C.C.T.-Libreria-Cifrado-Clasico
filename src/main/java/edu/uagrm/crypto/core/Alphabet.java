package edu.uagrm.crypto.core;

public class Alphabet {

    public static final String SPANISH = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    public static final String ENGLISH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERS = "0123456789";
    public static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;':\",./<>?";
    public static final String ALPHANUMERIC = ENGLISH + NUMBERS;
    public static final String ALPHANUMERIC_SPANISH = SPANISH + NUMBERS;
    public static final String FULL_ASCII = ENGLISH + NUMBERS + SYMBOLS + " ";
    public static final String FULL_ASCII_SPANISH = SPANISH + NUMBERS + SYMBOLS + " ";

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

    public static Alphabet getNumbers() {
        return new Alphabet(NUMBERS);
    }

    public static Alphabet getAlphanumeric() {
        return new Alphabet(ALPHANUMERIC);
    }

    public static Alphabet getAlphanumericSpanish() {
        return new Alphabet(ALPHANUMERIC_SPANISH);
    }

    public static Alphabet getFullAscii() {
        return new Alphabet(FULL_ASCII);
    }

    public static Alphabet getFullAsciiSpanish() {
        return new Alphabet(FULL_ASCII_SPANISH);
    }

    public static Alphabet createCustom(String customChars) {
        return new Alphabet(customChars);
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