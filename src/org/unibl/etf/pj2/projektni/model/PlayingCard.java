package org.unibl.etf.pj2.projektni.model;

public class PlayingCard {
    int number;
    String imagePath;
    String meaning; //specijalna ili obicna

    public PlayingCard() {
        super();
    }

    public PlayingCard(int number, String imagePath, String meaning) {
        this.number = number;
        this.imagePath = imagePath;
        this.meaning = meaning;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

}
