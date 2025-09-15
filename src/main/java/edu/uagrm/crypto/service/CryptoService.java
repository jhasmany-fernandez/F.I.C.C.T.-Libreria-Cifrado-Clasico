package edu.uagrm.crypto.service;

import edu.uagrm.crypto.core.*;
import edu.uagrm.crypto.algorithms.*;
import edu.uagrm.crypto.analysis.*;
import java.util.HashMap;
import java.util.Map;

public class CryptoService {
    
    private Map<String, CipherAlgorithm> algorithms;
    private Alphabet defaultAlphabet;
    private TextOptions defaultOptions;
    
    public CryptoService() {
        this.defaultAlphabet = Alphabet.getSpanish();
        this.defaultOptions = new TextOptions();
        initializeAlgorithms();
    }
    
    public CryptoService(Alphabet alphabet) {
        this.defaultAlphabet = alphabet;
        this.defaultOptions = new TextOptions();
        initializeAlgorithms();
    }
    
    private void initializeAlgorithms() {
        algorithms = new HashMap<>();
        algorithms.put("caesar", new CaesarCipher(defaultAlphabet));
        algorithms.put("vigenere", new VigenereCipher(defaultAlphabet));
        algorithms.put("substitution", new SimpleSubstitutionCipher(defaultAlphabet));
        algorithms.put("atbash", new AtbashCipher(defaultAlphabet));
        algorithms.put("railfence", new RailFenceCipher());
        algorithms.put("columnar", new ColumnarTranspositionCipher());
        algorithms.put("playfair", new PlayfairCipher());
        algorithms.put("xor", new XorCipher());
    }
    
    public CryptoResult encrypt(String algorithm, String plaintext, String key, TextOptions options) {
        try {
            CipherAlgorithm cipher = getAlgorithm(algorithm);
            if (cipher == null) {
                return CryptoResult.error("Algoritmo no encontrado: " + algorithm);
            }
            
            if (!cipher.isValidKey(key)) {
                return CryptoResult.error("Clave inválida para " + cipher.getName());
            }
            
            TextOptions processOptions = options != null ? options : defaultOptions;
            String normalizedText = TextPreprocessor.normalize(plaintext, processOptions, defaultAlphabet);
            String encrypted = cipher.encrypt(normalizedText, key);
            
            return CryptoResult.success(encrypted, "Texto cifrado exitosamente con " + cipher.getName());
            
        } catch (Exception e) {
            return CryptoResult.error("Error durante el cifrado: " + e.getMessage());
        }
    }
    
    public CryptoResult decrypt(String algorithm, String ciphertext, String key, TextOptions options) {
        try {
            CipherAlgorithm cipher = getAlgorithm(algorithm);
            if (cipher == null) {
                return CryptoResult.error("Algoritmo no encontrado: " + algorithm);
            }
            
            if (!cipher.isValidKey(key)) {
                return CryptoResult.error("Clave inválida para " + cipher.getName());
            }
            
            TextOptions processOptions = options != null ? options : defaultOptions;
            String normalizedText = TextPreprocessor.normalize(ciphertext, processOptions, defaultAlphabet);
            String decrypted = cipher.decrypt(normalizedText, key);
            
            return CryptoResult.success(decrypted, "Texto descifrado exitosamente con " + cipher.getName());
            
        } catch (Exception e) {
            return CryptoResult.error("Error durante el descifrado: " + e.getMessage());
        }
    }
    
    public AnalysisResult analyzeFrequencies(String text) {
        try {
            FrequencyAnalyzer analyzer = new FrequencyAnalyzer(defaultAlphabet);
            String normalizedText = TextPreprocessor.normalize(text, defaultOptions, defaultAlphabet);
            
            Map<Character, Double> frequencies = analyzer.getCharacterPercentages(normalizedText);
            double indexOfCoincidence = analyzer.getIndexOfCoincidence(normalizedText);
            String report = analyzer.getFrequencyReport(normalizedText);
            
            return AnalysisResult.success(frequencies, indexOfCoincidence, report);
            
        } catch (Exception e) {
            return AnalysisResult.error("Error durante el análisis: " + e.getMessage());
        }
    }
    
    public CaesarBreakResult breakCaesar(String ciphertext) {
        try {
            CaesarBreaker breaker = new CaesarBreaker(defaultAlphabet);
            String normalizedText = TextPreprocessor.normalize(ciphertext, defaultOptions, defaultAlphabet);
            
            CaesarBreaker.CaesarCandidate best = breaker.findBestCandidate(normalizedText);
            java.util.List<CaesarBreaker.CaesarCandidate> allCandidates = breaker.bruteForce(normalizedText);
            
            return CaesarBreakResult.success(best, allCandidates);
            
        } catch (Exception e) {
            return CaesarBreakResult.error("Error durante el análisis de César: " + e.getMessage());
        }
    }
    
    private CipherAlgorithm getAlgorithm(String name) {
        return algorithms.get(name.toLowerCase());
    }
    
    public Map<String, String> getAvailableAlgorithms() {
        Map<String, String> available = new HashMap<>();
        for (Map.Entry<String, CipherAlgorithm> entry : algorithms.entrySet()) {
            available.put(entry.getKey(), entry.getValue().getName());
        }
        return available;
    }
    
    // Métodos con selección dinámica de alfabeto
    public CryptoResult encrypt(String algorithm, String plaintext, String key, TextOptions options, String alphabetType) {
        try {
            Alphabet selectedAlphabet = getAlphabetByType(alphabetType);
            CipherAlgorithm cipher = getAlgorithmWithAlphabet(algorithm, selectedAlphabet);
            if (cipher == null) {
                return CryptoResult.error("Algoritmo no encontrado: " + algorithm);
            }
            
            if (!cipher.isValidKey(key)) {
                return CryptoResult.error("Clave inválida para " + cipher.getName());
            }
            
            TextOptions processOptions = options != null ? options : defaultOptions;
            String normalizedText = TextPreprocessor.normalize(plaintext, processOptions, selectedAlphabet);
            String encrypted = cipher.encrypt(normalizedText, key);
            
            return CryptoResult.success(encrypted, "Texto cifrado exitosamente con " + cipher.getName() + " (Alfabeto " + alphabetType + ")");
            
        } catch (Exception e) {
            return CryptoResult.error("Error durante el cifrado: " + e.getMessage());
        }
    }
    
    public CryptoResult decrypt(String algorithm, String ciphertext, String key, TextOptions options, String alphabetType) {
        try {
            Alphabet selectedAlphabet = getAlphabetByType(alphabetType);
            CipherAlgorithm cipher = getAlgorithmWithAlphabet(algorithm, selectedAlphabet);
            if (cipher == null) {
                return CryptoResult.error("Algoritmo no encontrado: " + algorithm);
            }
            
            if (!cipher.isValidKey(key)) {
                return CryptoResult.error("Clave inválida para " + cipher.getName());
            }
            
            TextOptions processOptions = options != null ? options : defaultOptions;
            String normalizedText = TextPreprocessor.normalize(ciphertext, processOptions, selectedAlphabet);
            String decrypted = cipher.decrypt(normalizedText, key);
            
            return CryptoResult.success(decrypted, "Texto descifrado exitosamente con " + cipher.getName() + " (Alfabeto " + alphabetType + ")");
            
        } catch (Exception e) {
            return CryptoResult.error("Error durante el descifrado: " + e.getMessage());
        }
    }
    
    private Alphabet getAlphabetByType(String alphabetType) {
        if ("english".equalsIgnoreCase(alphabetType)) {
            return Alphabet.getEnglish();
        } else {
            return Alphabet.getSpanish();
        }
    }
    
    private CipherAlgorithm getAlgorithmWithAlphabet(String algorithmName, Alphabet alphabet) {
        switch (algorithmName.toLowerCase()) {
            case "caesar":
                return new CaesarCipher(alphabet);
            case "vigenere":
                return new VigenereCipher(alphabet);
            case "substitution":
                return new SimpleSubstitutionCipher(alphabet);
            case "atbash":
                return new AtbashCipher(alphabet);
            case "railfence":
                return new RailFenceCipher();
            case "columnar":
                return new ColumnarTranspositionCipher(alphabet);
            case "playfair":
                return new PlayfairCipher(alphabet);
            case "xor":
                return new XorCipher();
            default:
                return null;
        }
    }
    
    public static class CryptoResult {
        private boolean success;
        private String result;
        private String message;
        
        private CryptoResult(boolean success, String result, String message) {
            this.success = success;
            this.result = result;
            this.message = message;
        }
        
        public static CryptoResult success(String result, String message) {
            return new CryptoResult(true, result, message);
        }
        
        public static CryptoResult error(String message) {
            return new CryptoResult(false, null, message);
        }
        
        public boolean isSuccess() { return success; }
        public String getResult() { return result; }
        public String getMessage() { return message; }
    }
    
    public static class AnalysisResult {
        private boolean success;
        private Map<Character, Double> frequencies;
        private double indexOfCoincidence;
        private String report;
        private String message;
        
        private AnalysisResult(boolean success, Map<Character, Double> frequencies, 
                             double ic, String report, String message) {
            this.success = success;
            this.frequencies = frequencies;
            this.indexOfCoincidence = ic;
            this.report = report;
            this.message = message;
        }
        
        public static AnalysisResult success(Map<Character, Double> frequencies, double ic, String report) {
            return new AnalysisResult(true, frequencies, ic, report, "Análisis completado");
        }
        
        public static AnalysisResult error(String message) {
            return new AnalysisResult(false, null, 0, null, message);
        }
        
        public boolean isSuccess() { return success; }
        public Map<Character, Double> getFrequencies() { return frequencies; }
        public double getIndexOfCoincidence() { return indexOfCoincidence; }
        public String getReport() { return report; }
        public String getMessage() { return message; }
    }
    
    public static class CaesarBreakResult {
        private boolean success;
        private CaesarBreaker.CaesarCandidate bestCandidate;
        private java.util.List<CaesarBreaker.CaesarCandidate> allCandidates;
        private String message;
        
        private CaesarBreakResult(boolean success, CaesarBreaker.CaesarCandidate best,
                                java.util.List<CaesarBreaker.CaesarCandidate> all, String message) {
            this.success = success;
            this.bestCandidate = best;
            this.allCandidates = all;
            this.message = message;
        }
        
        public static CaesarBreakResult success(CaesarBreaker.CaesarCandidate best,
                                              java.util.List<CaesarBreaker.CaesarCandidate> all) {
            return new CaesarBreakResult(true, best, all, "Análisis de César completado");
        }
        
        public static CaesarBreakResult error(String message) {
            return new CaesarBreakResult(false, null, null, message);
        }
        
        public boolean isSuccess() { return success; }
        public CaesarBreaker.CaesarCandidate getBestCandidate() { return bestCandidate; }
        public java.util.List<CaesarBreaker.CaesarCandidate> getAllCandidates() { return allCandidates; }
        public String getMessage() { return message; }
    }
}