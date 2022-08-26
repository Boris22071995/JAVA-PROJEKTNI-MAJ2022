package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayingDeck{
    PlayingCard[] cards = new PlayingCard[52];
    ImageView imageView;
    List<String> stringList= new ArrayList<String>();
    Label meaningOfCard;
    List<PlayingCard> deck = new ArrayList<>();

    /*
     File file = new File("karte/1.png");
        Image image = new Image(file.toURI().toString());
        //imageView = new ImageView(image);
        imageView.setImage(image);
     */

    public PlayingDeck() {
        int br = 0;
        for(int i = 0; i < 40; i+=4)
            for(int j = 0; j < 4; j++) {
                cards[br++] = new PlayingCard(j+1,"karte/"+ (j+1) +".png","Obicna karta, figura prelazi " + j+1 + "polja.");
            }
        for(int i = 40; i < 52; i++)
            cards[i]  = new PlayingCard(5,"karte/5.png","Specijalna karta, figura prelazi zadati broj polja.");
        shuffel();
        addStrings();
        for(int i = 0; i < cards.length; i++) {
            PlayingCard pc = cards[i];
            deck.add(pc);
        }
    }
    public void shuffel() {
        int prviBroj;
        int drugiBroj;
        PlayingCard card1 = new PlayingCard();
        PlayingCard card2 = new PlayingCard();
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
