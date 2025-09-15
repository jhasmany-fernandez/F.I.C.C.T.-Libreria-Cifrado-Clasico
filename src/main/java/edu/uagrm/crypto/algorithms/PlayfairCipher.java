package edu.uagrm.crypto.algorithms;

import edu.uagrm.crypto.core.CipherAlgorithm;
import edu.uagrm.crypto.core.Alphabet;

public class PlayfairCipher implements CipherAlgorithm {
    
    private Alphabet alphabet;
    private int gridRows;
    private int gridCols;
    
    public PlayfairCipher() {
        this.alphabet = Alphabet.getEnglish();
        calculateGridDimensions();
    }
    
    public PlayfairCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
        calculateGridDimensions();
    }
    
    private void calculateGridDimensions() {
        int size = alphabet.size();
        
        if (size == 25) {
            this.gridRows = 5;
            this.gridCols = 5;
        } else if (size == 26) {
            this.gridRows = 5;
            this.gridCols = 5;
        } else if (size == 27) {
            this.gridRows = 5;
            this.gridCols = 6;
        } else {
            int sqrt = (int) Math.sqrt(size);
            if (sqrt * sqrt == size) {
                this.gridRows = sqrt;
                this.gridCols = sqrt;
            } else {
                this.gridRows = sqrt + 1;
                this.gridCols = (int) Math.ceil((double) size / this.gridRows);
            }
        }
    }
    
    @Override
    public String encrypt(String plaintext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Playfair");
        }
        
        char[][] matrix = generateMatrix(key);
        String preparedText = prepareText(plaintext, true);
        
        return processText(preparedText, matrix, true);
    }
    
    @Override
    public String decrypt(String ciphertext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Playfair");
        }
        
        char[][] matrix = generateMatrix(key);
        return processText(ciphertext, matrix, false);
    }
    
    private char[][] generateMatrix(String key) {
        boolean[] used = new boolean[alphabet.size()];
        StringBuilder keyBuilder = new StringBuilder();
        
        String normalizedKey = key.toUpperCase();
        
        for (char c : normalizedKey.toCharArray()) {
            if (alphabet.contains(c)) {
                int index = alphabet.indexOf(c);
                if (!used[index]) {
                    keyBuilder.append(c);
                    used[index] = true;
                }
            }
        }
        
        for (int i = 0; i < alphabet.size(); i++) {
            if (!used[i]) {
                keyBuilder.append(alphabet.charAt(i));
            }
        }
        
        char[][] matrix = new char[gridRows][gridCols];
        String fullKey = keyBuilder.toString();
        
        for (int i = 0; i < Math.min(fullKey.length(), gridRows * gridCols); i++) {
            matrix[i / gridCols][i % gridCols] = fullKey.charAt(i);
        }
        
        return matrix;
    }
    
    private String prepareText(String text, boolean forEncryption) {
        StringBuilder prepared = new StringBuilder();
        String normalized = text.toUpperCase();
        
        for (int i = 0; i < normalized.length(); i++) {
            char c = normalized.charAt(i);
            if (alphabet.contains(c)) {
                prepared.append(c);
                
                if (forEncryption && i < normalized.length() - 1) {
                    char nextChar = normalized.charAt(i + 1);
                    if (alphabet.contains(nextChar) && c == nextChar) {
                        prepared.append(alphabet.charAt(0));
                    }
                }
            }
        }
        
        if (prepared.length() % 2 == 1) {
            prepared.append(alphabet.charAt(0));
        }
        
        return prepared.toString();
    }
    
    private String processText(String text, char[][] matrix, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < text.length(); i += 2) {
            if (i + 1 >= text.length()) break;
            
            char first = text.charAt(i);
            char second = text.charAt(i + 1);
            
            int[] pos1 = findPosition(matrix, first);
            int[] pos2 = findPosition(matrix, second);
            
            if (pos1 == null || pos2 == null) {
                result.append(first).append(second);
                continue;
            }
            
            if (pos1[0] == pos2[0]) {
                int shift = encrypt ? 1 : -1;
                result.append(matrix[pos1[0]][(pos1[1] + shift + gridCols) % gridCols]);
                result.append(matrix[pos2[0]][(pos2[1] + shift + gridCols) % gridCols]);
            } else if (pos1[1] == pos2[1]) {
                int shift = encrypt ? 1 : -1;
                result.append(matrix[(pos1[0] + shift + gridRows) % gridRows][pos1[1]]);
                result.append(matrix[(pos2[0] + shift + gridRows) % gridRows][pos2[1]]);
            } else {
                result.append(matrix[pos1[0]][pos2[1]]);
                result.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        
        return result.toString();
    }
    
    private int[] findPosition(char[][] matrix, char c) {
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridCols; j++) {
                if (matrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
    
    @Override
    public String getName() {
        return "Playfair";
    }
    
    @Override
    public boolean isValidKey(String key) {
        if (key == null || key.isEmpty()) return false;
        String normalized = key.toUpperCase();
        
        for (char c : normalized.toCharArray()) {
            if (alphabet.contains(c)) {
                return true;
            }
        }
        return false;
    }
}