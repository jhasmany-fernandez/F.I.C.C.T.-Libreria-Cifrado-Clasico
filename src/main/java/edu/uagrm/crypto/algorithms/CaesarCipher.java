package edu.uagrm.crypto.algorithms;

import edu.uagrm.crypto.core.CipherAlgorithm;
import edu.uagrm.crypto.core.Alphabet;
import edu.uagrm.crypto.core.Keys;

public class CaesarCipher implements CipherAlgorithm {
    
    private Alphabet alphabet;
    
    public CaesarCipher() {
        this.alphabet = Alphabet.getSpanish();
    }
    
    public CaesarCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }
    
    @Override
    public String encrypt(String plaintext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para César");
        }
        
        int shift = Integer.parseInt(key);
        return shift(plaintext, shift);
    }
    
    @Override
    public String decrypt(String ciphertext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para César");
        }
        
        int shift = Integer.parseInt(key);
        return shift(ciphertext, -shift);
    }
    
    private String shift(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (alphabet.contains(c)) {
                boolean isLowerCase = Character.isLowerCase(c);
                int index = alphabet.indexOf(c);
                int newIndex = (index + shift + alphabet.size()) % alphabet.size();
                char shiftedChar = alphabet.charAt(newIndex);

                if (isLowerCase) {
                    result.append(Character.toLowerCase(shiftedChar));
                } else {
                    result.append(shiftedChar);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
    
    @Override
    public String getName() {
        return "César";
    }
    
    @Override
    public boolean isValidKey(String key) {
        return Keys.isValidCaesarKey(key);
    }
}