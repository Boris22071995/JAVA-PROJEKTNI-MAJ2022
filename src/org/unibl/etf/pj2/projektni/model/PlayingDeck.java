package org.unibl.etf.pj2.projektni.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PlayingDeck{
    PlayingCard[] cards = new PlayingCard[52];
    RegularPlayingCard[] regularCards = new RegularPlayingCard[40];
    SpecialPlayingCard[] specialCards = new SpecialPlayingCard[12];
    List<String> stringList= new ArrayList<>();
    List<PlayingCard> deck = new ArrayList<>();
    public PlayingDeck() {
        int br = 0;
        int br2 = 0;
        for(int i = 0; i < 40; i+=4)
            for(int j = 0; j < 4; j++) {
                regularCards[br++] = new RegularPlayingCard(j+1, System.getProperty("user.dir") + File.separator + "karte" + File.separator + (j+1) + ".png");
            }
        for(int i = 40; i < 52; i++)
            specialCards[br2++] = new SpecialPlayingCard(5,System.getProperty("user.dir") + File.separator + "karte" + File.separator + "5.png");
        System.arraycopy(regularCards, 0, cards, 0, 40);
        int temp = 0;
        for(int i = 40; i < 52; i++) {
            cards[i] = specialCards[temp++];
        }
        shuffel();
        shuffel();
        shuffel();
        addStrings();
        Collections.addAll(deck, cards);
    }
    public void shuffel() {
        int prviBroj;
        int drugiBroj;
        PlayingCard card1;
        PlayingCard card2;
        Random rand = new Random();
        for(int i = 0; i < 25; i++) {
            prviBroj = rand.nextInt(52);
            drugiBroj = rand.nextInt(52);
            card1 = cards[prviBroj];
            card2 = cards[drugiBroj];
            cards[prviBroj] = card2;
            cards[drugiBroj] = card1;
        }
    }
    public void addStrings() {
        for(int i = 0; i < 52; i++) {
            stringList.add(cards[i].getImagePath());
        }
    }
    public PlayingCard getCard() {
        PlayingCard pc = deck.remove(0);
        deck.add(pc);
        return pc;
    }
}
