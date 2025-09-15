package edu.uagrm.crypto.analysis;

import edu.uagrm.crypto.algorithms.CaesarCipher;
import edu.uagrm.crypto.core.Alphabet;
import java.util.*;

public class CaesarBreaker {
    
    private Alphabet alphabet;
    private FrequencyAnalyzer analyzer;
    
    private static final Map<Character, Double> SPANISH_FREQUENCIES = new HashMap<>();
    static {
        SPANISH_FREQUENCIES.put('E', 13.68);
        SPANISH_FREQUENCIES.put('A', 12.53);
        SPANISH_FREQUENCIES.put('O', 8.68);
        SPANISH_FREQUENCIES.put('S', 7.98);
        SPANISH_FREQUENCIES.put('R', 6.87);
        SPANISH_FREQUENCIES.put('N', 6.71);
        SPANISH_FREQUENCIES.put('I', 6.25);
        SPANISH_FREQUENCIES.put('D', 5.86);
        SPANISH_FREQUENCIES.put('L', 4.97);
        SPANISH_FREQUENCIES.put('C', 4.68);
        SPANISH_FREQUENCIES.put('T', 4.63);
        SPANISH_FREQUENCIES.put('U', 3.93);
        SPANISH_FREQUENCIES.put('M', 3.15);
        SPANISH_FREQUENCIES.put('P', 2.51);
        SPANISH_FREQUENCIES.put('B', 2.22);
        SPANISH_FREQUENCIES.put('G', 1.01);
        SPANISH_FREQUENCIES.put('V', 0.90);
        SPANISH_FREQUENCIES.put('Y', 0.90);
        SPANISH_FREQUENCIES.put('F', 0.70);
        SPANISH_FREQUENCIES.put('Q', 0.88);
        SPANISH_FREQUENCIES.put('H', 0.70);
        SPANISH_FREQUENCIES.put('Z', 0.52);
        SPANISH_FREQUENCIES.put('J', 0.44);
        SPANISH_FREQUENCIES.put('Ñ', 0.31);
        SPANISH_FREQUENCIES.put('X', 0.22);
        SPANISH_FREQUENCIES.put('K', 0.02);
        SPANISH_FREQUENCIES.put('W', 0.02);
    }
    
    public CaesarBreaker() {
        this.alphabet = Alphabet.getSpanish();
        this.analyzer = new FrequencyAnalyzer(alphabet);
    }
    
    public CaesarBreaker(Alphabet alphabet) {
        this.alphabet = alphabet;
        this.analyzer = new FrequencyAnalyzer(alphabet);
    }
    
    public List<CaesarCandidate> bruteForce(String ciphertext) {
        List<CaesarCandidate> candidates = new ArrayList<>();
        CaesarCipher cipher = new CaesarCipher(alphabet);
        
        for (int shift = 0; shift < alphabet.size(); shift++) {
            String decrypted = cipher.decrypt(ciphertext, String.valueOf(shift));
            double score = calculateFitnessScore(decrypted);
            candidates.add(new CaesarCandidate(shift, decrypted, score));
        }
        
        candidates.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));
        return candidates;
    }
    
    public CaesarCandidate findBestCandidate(String ciphertext) {
        List<CaesarCandidate> candidates = bruteForce(ciphertext);
        return candidates.isEmpty() ? null : candidates.get(0);
    }
    
    public int findMostLikelyShift(String ciphertext) {
        Map<Character, Integer> frequencies = analyzer.getCharacterFrequencies(ciphertext);
        char mostFrequent = getMostFrequentChar(frequencies);
        
        char expectedMostFrequent = 'E';
        
        int shift = (alphabet.indexOf(mostFrequent) - alphabet.indexOf(expectedMostFrequent) + alphabet.size()) % alphabet.size();
        return shift;
    }
    
    private char getMostFrequentChar(Map<Character, Integer> frequencies) {
        return frequencies.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse('A');
    }
    
    private double calculateFitnessScore(String text) {
        Map<Character, Double> textFreqs = analyzer.getCharacterPercentages(text);
        double score = 0;
        
        for (Map.Entry<Character, Double> entry : textFreqs.entrySet()) {
            char c = entry.getKey();
            double textFreq = entry.getValue();
            double expectedFreq = SPANISH_FREQUENCIES.getOrDefault(c, 0.0);
            
            score -= Math.abs(textFreq - expectedFreq);
        }
        
        return score;
    }
    
    public static class CaesarCandidate {
        private int shift;
        private String text;
        private double score;
        
        public CaesarCandidate(int shift, String text, double score) {
            this.shift = shift;
            this.text = text;
            this.score = score;
        }
        
        public int getShift() { return shift; }
        public String getText() { return text; }
        public double getScore() { return score; }
        
        @Override
        public String toString() {
            return String.format("Desplazamiento %d (Score: %.2f): %s", 
                shift, score, text.length() > 50 ? text.substring(0, 50) + "..." : text);
        }
    }
}