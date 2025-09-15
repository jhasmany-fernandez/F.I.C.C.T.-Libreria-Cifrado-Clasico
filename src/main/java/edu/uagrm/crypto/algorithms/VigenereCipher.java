package edu.uagrm.crypto.algorithms;

import edu.uagrm.crypto.core.CipherAlgorithm;
import edu.uagrm.crypto.core.Alphabet;
import edu.uagrm.crypto.core.Keys;

public class VigenereCipher implements CipherAlgorithm {
    
    private Alphabet alphabet;
    
    public VigenereCipher() {
        this.alphabet = Alphabet.getEnglish();
    }
    
    public VigenereCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }
    
    @Override
    public String encrypt(String plaintext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Vigenère");
        }
        
        return process(plaintext, key, true);
    }
    
    @Override
    public String decrypt(String ciphertext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Vigenère");
        }
        
        return process(ciphertext, key, false);
    }
    
    private String process(String text, String key, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        String normalizedKey = key.toUpperCase();
        int keyIndex = 0;
        
        for (char c : text.toCharArray()) {
            if (alphabet.contains(c)) {
                int textIndex = alphabet.indexOf(c);
                int keyCharIndex = alphabet.indexOf(normalizedKey.charAt(keyIndex % normalizedKey.length()));
                
                int shift = encrypt ? keyCharIndex : -keyCharIndex;
                int newIndex = (textIndex + shift + alphabet.size()) % alphabet.size();
                
                result.append(alphabet.charAt(newIndex));
                keyIndex++;
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    @Override
    public String getName() {
        return "Vigenère";
    }
    
    @Override
    public boolean isValidKey(String key) {
        return Keys.isValidVigenereKey(key, alphabet);
    }
}