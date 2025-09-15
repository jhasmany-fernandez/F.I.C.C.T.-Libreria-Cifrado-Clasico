package edu.uagrm.crypto.algorithms;

import edu.uagrm.crypto.core.CipherAlgorithm;

public class RailFenceCipher implements CipherAlgorithm {
    
    @Override
    public String encrypt(String plaintext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Rail Fence");
        }
        
        int rails = Integer.parseInt(key);
        if (rails <= 1 || plaintext.length() <= rails) {
            return plaintext;
        }
        
        String[] fence = new String[rails];
        for (int i = 0; i < rails; i++) {
            fence[i] = "";
        }
        
        int rail = 0;
        boolean down = true;
        
        for (char c : plaintext.toCharArray()) {
            fence[rail] += c;
            
            if (down) {
                rail++;
                if (rail == rails - 1) {
                    down = false;
                }
            } else {
                rail--;
                if (rail == 0) {
                    down = true;
                }
            }
        }
        
        StringBuilder result = new StringBuilder();
        for (String line : fence) {
            result.append(line);
        }
        
        return result.toString();
    }
    
    @Override
    public String decrypt(String ciphertext, String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException("Clave inválida para Rail Fence");
        }
        
        int rails = Integer.parseInt(key);
        if (rails <= 1 || ciphertext.length() <= rails) {
            return ciphertext;
        }
        
        int[] railLengths = new int[rails];
        int rail = 0;
        boolean down = true;
        
        for (int i = 0; i < ciphertext.length(); i++) {
            railLengths[rail]++;
            
            if (down) {
                rail++;
                if (rail == rails - 1) {
                    down = false;
                }
            } else {
                rail--;
                if (rail == 0) {
                    down = true;
                }
            }
        }
        
        String[] fence = new String[rails];
        int index = 0;
        for (int i = 0; i < rails; i++) {
            fence[i] = ciphertext.substring(index, index + railLengths[i]);
            index += railLengths[i];
        }
        
        StringBuilder result = new StringBuilder();
        rail = 0;
        down = true;
        int[] positions = new int[rails];
        
        for (int i = 0; i < ciphertext.length(); i++) {
            result.append(fence[rail].charAt(positions[rail]++));
            
            if (down) {
                rail++;
                if (rail == rails - 1) {
                    down = false;
                }
            } else {
                rail--;
                if (rail == 0) {
                    down = true;
                }
            }
        }
        
        return result.toString();
    }
    
    @Override
    public String getName() {
        return "Rail Fence";
    }
    
    @Override
    public boolean isValidKey(String key) {
        try {
            int rails = Integer.parseInt(key);
            return rails > 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}