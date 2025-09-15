package edu.uagrm.crypto.algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import edu.uagrm.crypto.core.Alphabet;

public class CaesarCipherTest {
    
    @Test
    public void testEncryptBasic() {
        CaesarCipher cipher = new CaesarCipher(Alphabet.getEnglish());
        String result = cipher.encrypt("HOLA", "3");
        assertEquals("KROD", result);
    }
    
    @Test
    public void testDecryptBasic() {
        CaesarCipher cipher = new CaesarCipher(Alphabet.getEnglish());
        String result = cipher.decrypt("KROD", "3");
        assertEquals("HOLA", result);
    }
    
    @Test
    public void testEncryptDecryptRoundTrip() {
        CaesarCipher cipher = new CaesarCipher(Alphabet.getEnglish());
        String original = "MENSAJE SECRETO";
        String encrypted = cipher.encrypt(original, "7");
        String decrypted = cipher.decrypt(encrypted, "7");
        assertEquals(original, decrypted);
    }
    
    @Test
    public void testWrapAround() {
        CaesarCipher cipher = new CaesarCipher(Alphabet.getEnglish());
        String result = cipher.encrypt("XYZ", "3");
        assertEquals("ABC", result);
    }
    
    @Test
    public void testValidKey() {
        CaesarCipher cipher = new CaesarCipher(Alphabet.getEnglish());
        assertTrue(cipher.isValidKey("0"));
        assertTrue(cipher.isValidKey("25"));
        assertTrue(cipher.isValidKey("13"));
        assertFalse(cipher.isValidKey("26"));
        assertFalse(cipher.isValidKey("-1"));
        assertFalse(cipher.isValidKey("abc"));
    }
    
    @Test
    public void testInvalidKeyEncrypt() {
        CaesarCipher cipher = new CaesarCipher(Alphabet.getEnglish());
        assertThrows(IllegalArgumentException.class, () -> {
            cipher.encrypt("TEST", "invalid");
        });
    }
    
    @Test
    public void testInvalidKeyDecrypt() {
        CaesarCipher cipher = new CaesarCipher(Alphabet.getEnglish());
        assertThrows(IllegalArgumentException.class, () -> {
            cipher.decrypt("TEST", "26");
        });
    }
    
    @Test
    public void testGetName() {
        CaesarCipher cipher = new CaesarCipher(Alphabet.getEnglish());
        assertEquals("César", cipher.getName());
    }
    
    @Test
    public void testCustomAlphabet() {
        Alphabet customAlphabet = new Alphabet("ABCDEF");
        CaesarCipher cipher = new CaesarCipher(customAlphabet);
        String result = cipher.encrypt("ABC", "2");
        assertEquals("CDE", result);
    }
}