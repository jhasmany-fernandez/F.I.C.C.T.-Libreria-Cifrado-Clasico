package edu.uagrm.crypto.analysis;

import edu.uagrm.crypto.core.Alphabet;
import java.util.*;

public class FrequencyAnalyzer {
    
    private Alphabet alphabet;
    
    public FrequencyAnalyzer() {
        this.alphabet = Alphabet.getSpanish();
    }
    
    public FrequencyAnalyzer(Alphabet alphabet) {
        this.alphabet = alphabet;
    }
    
    public Map<Character, Integer> getCharacterFrequencies(String text) {
        Map<Character, Integer> frequencies = new LinkedHashMap<>();
        
        for (char c : alphabet.getChars().toCharArray()) {
            frequencies.put(c, 0);
        }
        
        for (char c : text.toCharArray()) {
            if (alphabet.contains(c)) {
                frequencies.put(Character.toUpperCase(c), 
                    frequencies.get(Character.toUpperCase(c)) + 1);
            }
        }
        
        return frequencies;
    }
    
    public Map<Character, Double> getCharacterPercentages(String text) {
        Map<Character, Integer> frequencies = getCharacterFrequencies(text);
        Map<Character, Double> percentages = new LinkedHashMap<>();
        
        int total = frequencies.values().stream().mapToInt(Integer::intValue).sum();
        
        if (total == 0) {
            return percentages;
        }
        
        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            double percentage = (double) entry.getValue() / total * 100;
            percentages.put(entry.getKey(), percentage);
        }
        
        return percentages;
    }
    
    public List<Map.Entry<Character, Integer>> getMostFrequentChars(String text, int limit) {
        Map<Character, Integer> frequencies = getCharacterFrequencies(text);
        
        return frequencies.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(ArrayList::new, (list, entry) -> list.add(entry), ArrayList::addAll);
    }
    
    public double getIndexOfCoincidence(String text) {
        Map<Character, Integer> frequencies = getCharacterFrequencies(text);
        int total = frequencies.values().stream().mapToInt(Integer::intValue).sum();
        
        if (total <= 1) {
            return 0;
        }
        
        double ic = 0;
        for (int freq : frequencies.values()) {
            ic += freq * (freq - 1);
        }
        
        return ic / (total * (total - 1));
    }
    
    public String getFrequencyReport(String text) {
        StringBuilder report = new StringBuilder();
        Map<Character, Double> percentages = getCharacterPercentages(text);
        
        report.append("Análisis de Frecuencias:\n");
        report.append("========================\n\n");
        
        for (Map.Entry<Character, Double> entry : percentages.entrySet()) {
            if (entry.getValue() > 0) {
                report.append(String.format("%c: %.2f%%\n", 
                    entry.getKey(), entry.getValue()));
            }
        }
        
        report.append(String.format("\nÍndice de Coincidencia: %.4f\n", 
            getIndexOfCoincidence(text)));
        
        return report.toString();
    }
}