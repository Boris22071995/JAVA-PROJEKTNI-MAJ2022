package org.unibl.etf.pj2.projektni.model;

import org.unibl.etf.pj2.projektni.interfaces.MeaningOfCard;

public class PlayingCard implements MeaningOfCard{
    int number;
    String imagePath;

    public PlayingCard() {
        super();
    }

    public PlayingCard(int number, String imagePath) {
        this.number = number;
        this.imagePath = imagePath;
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

    @Override
    public String meaning() {
        return "Card";
    }
}
