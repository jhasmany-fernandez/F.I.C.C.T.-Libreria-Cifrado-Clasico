package edu.uagrm.crypto.algorithms;

import edu.uagrm.crypto.core.CipherAlgorithm;

public class XorCipher implements CipherAlgorithm {
    
    @Override
    public String encrypt(String plaintext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para XOR");
        }
        
        return processXor(plaintext, key);
    }
    
    @Override
    public String decrypt(String ciphertext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para XOR");
        }
        
        return processXor(ciphertext, key);
    }
    
    private String processXor(String text, String key) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char xorChar = (char) (textChar ^ keyChar);
            result.append(xorChar);
        }
        
        return result.toString();
    }
    
    @Override
    public String getName() {
        return "XOR";
    }
    
    @Override
    public boolean isValidKey(String key) {
        return key != null && !key.isEmpty();
    }
}