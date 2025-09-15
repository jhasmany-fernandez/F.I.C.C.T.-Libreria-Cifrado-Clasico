package edu.uagrm.crypto.algorithms;

import edu.uagrm.crypto.core.CipherAlgorithm;
import edu.uagrm.crypto.core.Alphabet;

public class AtbashCipher implements CipherAlgorithm {
    
    private Alphabet alphabet;
    
    public AtbashCipher() {
        this.alphabet = Alphabet.getSpanish();
    }
    
    public AtbashCipher(Alphabet alphabet) {
        this.alphabet = alphabet;
    }
    
    @Override
    public String encrypt(String plaintext, String key) {
        return process(plaintext);
    }
    
    @Override
    public String decrypt(String ciphertext, String key) {
        return process(ciphertext);
    }
    
    private String process(String text) {
        StringBuilder result = new StringBuilder();
        
        for (char c : text.toCharArray()) {
            if (alphabet.contains(c)) {
                int index = alphabet.indexOf(c);
                int reversedIndex = alphabet.size() - 1 - index;
                result.append(alphabet.charAt(reversedIndex));
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
    
    @Override
    public String getName() {
        return "Atbash";
    }
    
    @Override
    public boolean isValidKey(String key) {
        return true;
    }
}