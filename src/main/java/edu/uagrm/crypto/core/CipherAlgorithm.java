package edu.uagrm.crypto.core;

public interface CipherAlgorithm {
    
    String encrypt(String plaintext, String key);
    
    String decrypt(String ciphertext, String key);
    
    String getName();
    
    boolean isValidKey(String key);
}