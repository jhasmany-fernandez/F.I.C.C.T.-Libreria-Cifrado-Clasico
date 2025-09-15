package edu.uagrm.crypto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import edu.uagrm.crypto.core.TextOptions;
import edu.uagrm.crypto.core.Alphabet;

public class CryptoServiceTest {
    
    private CryptoService cryptoService;
    
    @BeforeEach
    public void setUp() {
        cryptoService = new CryptoService(Alphabet.getEnglish());
    }
    
    @Test
    public void testEncryptCaesar() {
        CryptoService.CryptoResult result = cryptoService.encrypt("caesar", "HOLA", "3", null);
        assertTrue(result.isSuccess());
        assertEquals("KROD", result.getResult());
    }
    
    @Test
    public void testDecryptCaesar() {
        CryptoService.CryptoResult result = cryptoService.decrypt("caesar", "KROD", "3", null);
        assertTrue(result.isSuccess());
        assertEquals("HOLA", result.getResult());
    }
    
    @Test
    public void testInvalidAlgorithm() {
        CryptoService.CryptoResult result = cryptoService.encrypt("invalid", "TEST", "3", null);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Algoritmo no encontrado"));
    }
    
    @Test
    public void testInvalidKey() {
        CryptoService.CryptoResult result = cryptoService.encrypt("caesar", "TEST", "invalid", null);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Clave inválida"));
    }
    
    @Test
    public void testVigenereEncryption() {
        CryptoService.CryptoResult result = cryptoService.encrypt("vigenere", "ATACAR", "CLAVE", null);
        assertTrue(result.isSuccess());
        assertNotNull(result.getResult());
    }
    
    @Test
    public void testTextOptionsPreserveSpaces() {
        TextOptions options = new TextOptions();
        options.setPreserveSpaces(true);
        
        CryptoService.CryptoResult result = cryptoService.encrypt("caesar", "HOLA MUNDO", "3", options);
        assertTrue(result.isSuccess());
        assertTrue(result.getResult().contains(" "));
    }
    
    @Test
    public void testFrequencyAnalysis() {
        CryptoService.AnalysisResult result = cryptoService.analyzeFrequencies("AAABBC");
        assertTrue(result.isSuccess());
        assertNotNull(result.getFrequencies());
        assertTrue(result.getIndexOfCoincidence() > 0);
    }
    
    @Test
    public void testCaesarBreaker() {
        String ciphertext = "KROD";
        CryptoService.CaesarBreakResult result = cryptoService.breakCaesar(ciphertext);
        assertTrue(result.isSuccess());
        assertNotNull(result.getBestCandidate());
        assertNotNull(result.getAllCandidates());
        assertFalse(result.getAllCandidates().isEmpty());
    }
    
    @Test
    public void testGetAvailableAlgorithms() {
        Map<String, String> algorithms = cryptoService.getAvailableAlgorithms();
        assertNotNull(algorithms);
        assertTrue(algorithms.containsKey("caesar"));
        assertTrue(algorithms.containsKey("vigenere"));
        assertTrue(algorithms.containsKey("substitution"));
        assertTrue(algorithms.containsKey("atbash"));
    }
    
    @Test
    public void testAtbashCipher() {
        CryptoService.CryptoResult encryptResult = cryptoService.encrypt("atbash", "ABC", "", null);
        assertTrue(encryptResult.isSuccess());
        
        CryptoService.CryptoResult decryptResult = cryptoService.decrypt("atbash", encryptResult.getResult(), "", null);
        assertTrue(decryptResult.isSuccess());
        assertEquals("ABC", decryptResult.getResult());
    }
    
    @Test
    public void testRailFenceCipher() {
        CryptoService.CryptoResult result = cryptoService.encrypt("railfence", "HELLOWORLD", "3", null);
        assertTrue(result.isSuccess());
        assertNotNull(result.getResult());
        
        CryptoService.CryptoResult decryptResult = cryptoService.decrypt("railfence", result.getResult(), "3", null);
        assertTrue(decryptResult.isSuccess());
        assertEquals("HELLOWORLD", decryptResult.getResult());
    }
}