package org.unibl.etf.pj2.projektni.model;

public class SpecialPlayingCard extends PlayingCard{
    public SpecialPlayingCard(int number, String imagePath){
        super(number, imagePath);
    }
    @Override
    public String meaning() {
        return "Special card";
    }
}
