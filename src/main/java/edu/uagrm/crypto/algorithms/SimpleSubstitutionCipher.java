package edu.uagrm.crypto.algorithms;

import edu.uagrm.crypto.core.CipherAlgorithm;
import edu.uagrm.crypto.core.Alphabet;
import edu.uagrm.crypto.core.Keys;

public class SimpleSubstitutionCipher implements CipherAlgorithm {
    
    private Alphabet alphabet;
    
    public SimpleSubstitutionCipher() {
        this.alphabet = Alphabet.getSpanish();
    }
    
    public SimpleSubstitutionCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }
    
    @Override
    public String encrypt(String plaintext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Sustitución Simple");
        }
        
        return substitute(plaintext, alphabet.getChars(), key.toUpperCase());
    }
    
    @Override
    public String decrypt(String ciphertext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Sustitución Simple");
        }
        
        return substitute(ciphertext, key.toUpperCase(), alphabet.getChars());
    }
    
    private String substitute(String text, String from, String to) {
        StringBuilder result = new StringBuilder();
        
        for (char c : text.toCharArray()) {
            int index = from.indexOf(c);
            if (index != -1) {
                result.append(to.charAt(index));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    @Override
    public String getName() {
        return "Sustitución Simple";
    }
    
    @Override
    public boolean isValidKey(String key) {
        return Keys.isValidSubstitutionKey(key, alphabet);
    }
}