package edu.uagrm.crypto.core;

public class TextOptions {
    
    private boolean preserveCase = false;
    private boolean preserveSpaces = false;
    private boolean preserveAccents = false;
    private boolean preservePunctuation = false;
    
    public TextOptions() {}
    
    public TextOptions(boolean preserveCase, boolean preserveSpaces, 
                      boolean preserveAccents, boolean preservePunctuation) {
        this.preserveCase = preserveCase;
        this.preserveSpaces = preserveSpaces;
        this.preserveAccents = preserveAccents;
        this.preservePunctuation = preservePunctuation;
    }
    
    public boolean isPreserveCase() {
        return preserveCase;
    }
    
    public void setPreserveCase(boolean preserveCase) {
        this.preserveCase = preserveCase;
    }
    
    public boolean isPreserveSpaces() {
        return preserveSpaces;
    }
    
    public void setPreserveSpaces(boolean preserveSpaces) {
        this.preserveSpaces = preserveSpaces;
    }
    
    public boolean isPreserveAccents() {
        return preserveAccents;
    }
    
    public void setPreserveAccents(boolean preserveAccents) {
        this.preserveAccents = preserveAccents;
    }
    
    public boolean isPreservePunctuation() {
        return preservePunctuation;
    }
    
    public void setPreservePunctuation(boolean preservePunctuation) {
        this.preservePunctuation = preservePunctuation;
    }
}