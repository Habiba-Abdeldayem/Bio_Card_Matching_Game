package com.example.biomatchinggame;

public class Card {

    private int id;
    private boolean flipped;
    private int imageResourceId;
    private boolean matched;
    private String term;
    private String description;


    private boolean isExplanation;


    public Card(int id, String term, int imageResourceId, String description) {
        this.id = id;
        this.imageResourceId = imageResourceId;
        this.flipped = false;
        this.matched = false;
        this.term = term;
        this.description = description;
    }

    public boolean isExplanation() {
        return isExplanation;
    }
    public void setExplanation(boolean explanation) {
        isExplanation = explanation;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getId() {
        return id;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void flip() {
        flipped = !flipped;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
