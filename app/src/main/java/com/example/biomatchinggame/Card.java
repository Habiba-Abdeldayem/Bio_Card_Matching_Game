package com.example.biomatchinggame;

public class Card {

    private int id;
    private boolean flipped;
    private int imageResourceId;
    private boolean matched;

    public Card(int id, int imageResourceId) {
        this.id = id;
        this.imageResourceId = imageResourceId;
        this.flipped = false;
        this.matched = false;
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
}
