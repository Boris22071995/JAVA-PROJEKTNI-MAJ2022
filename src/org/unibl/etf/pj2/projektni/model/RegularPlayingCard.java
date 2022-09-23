package org.unibl.etf.pj2.projektni.model;

public class RegularPlayingCard extends PlayingCard{
    public RegularPlayingCard(int number, String imagePath) {
        super(number,imagePath);
    }
    @Override
    public String meaning() {
        return "Regular card";
    }
}
