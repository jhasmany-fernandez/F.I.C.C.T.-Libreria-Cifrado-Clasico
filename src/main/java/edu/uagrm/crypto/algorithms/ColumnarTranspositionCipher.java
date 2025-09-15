package edu.uagrm.crypto.algorithms;

import edu.uagrm.crypto.core.CipherAlgorithm;
import edu.uagrm.crypto.core.Alphabet;
import java.util.*;

public class ColumnarTranspositionCipher implements CipherAlgorithm {
    
    private Alphabet alphabet;
    
    public ColumnarTranspositionCipher() {
        this.alphabet = Alphabet.getEnglish();
    }
    
    public ColumnarTranspositionCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }
    
    @Override
    public String encrypt(String plaintext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Transposición Columnar");
        }
        
        String normalizedKey = normalizeKey(key);
        int[] keyOrder = getKeyOrder(normalizedKey);
        
        int cols = normalizedKey.length();
        int rows = (int) Math.ceil((double) plaintext.length() / cols);
        
        char[][] grid = new char[rows][cols];
        
        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (index < plaintext.length()) {
                    grid[r][c] = plaintext.charAt(index++);
                } else {
                    grid[r][c] = 'X';
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (int order : keyOrder) {
            for (int r = 0; r < rows; r++) {
                result.append(grid[r][order]);
            }
        }
        
        return result.toString();
    }
    
    @Override
    public String decrypt(String ciphertext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Transposición Columnar");
        }
        
        String normalizedKey = normalizeKey(key);
        int[] keyOrder = getKeyOrder(normalizedKey);
        
        int cols = normalizedKey.length();
        int rows = (int) Math.ceil((double) ciphertext.length() / cols);
        
        char[][] grid = new char[rows][cols];
        
        int index = 0;
        for (int order : keyOrder) {
            for (int r = 0; r < rows; r++) {
                if (index < ciphertext.length()) {
                    grid[r][order] = ciphertext.charAt(index++);
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] != '\u0000') {
                    result.append(grid[r][c]);
                }
            }
        }
        
        return result.toString().replaceAll("X+$", "");
    }
    
    private int[] getKeyOrder(String key) {
        List<Character> keyChars = new ArrayList<>();
        for (char c : key.toCharArray()) {
            keyChars.add(c);
        }
        
        List<Character> sortedChars = new ArrayList<>(keyChars);
        Collections.sort(sortedChars);
        
        int[] order = new int[key.length()];
        for (int i = 0; i < sortedChars.size(); i++) {
            char c = sortedChars.get(i);
            for (int j = 0; j < keyChars.size(); j++) {
                if (keyChars.get(j) == c) {
                    order[i] = j;
                    keyChars.set(j, '\u0000');
                    break;
                }
            }
        }
        
        return order;
    }
    
    @Override
    public String getName() {
        return "Transposición Columnar";
    }
    
    @Override
    public boolean isValidKey(String key) {
        if (key == null || key.isEmpty()) return false;
        String normalizedKey = normalizeKey(key);
        return normalizedKey.length() > 0;
    }
    
    private String normalizeKey(String key) {
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