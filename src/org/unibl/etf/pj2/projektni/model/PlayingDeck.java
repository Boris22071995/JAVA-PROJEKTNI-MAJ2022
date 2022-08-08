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

public class PlayingDeck extends Thread{
    PlayingCard[] cards = new PlayingCard[52];
    ImageView imageView;
    List<String> stringList= new ArrayList<String>();
    Label meaningOfCard;

    public PlayingDeck() {
        super();
    }

    /*
     File file = new File("karte/1.png");
        Image image = new Image(file.toURI().toString());
        //imageView = new ImageView(image);
        imageView.setImage(image);
     */

    public PlayingDeck(ImageView imageView, Label meaningOfCard) {
        this.imageView = imageView;
        this.meaningOfCard = meaningOfCard;
        int br = 0;
        for(int i = 0; i < 40; i+=4)
            for(int j = 0; j < 4; j++) {
                cards[br++] = new PlayingCard(j+1,"karte/"+ (j+1) +".png","Obicna karta, figura prelazi " + j+1 + "polja.");
            }
        for(int i = 40; i < 52; i++)
            cards[i]  = new PlayingCard(5,"karte/5.png","Specijalna karta, figura prelazi zadati broj polja.");
        shuffel();
        addStrings();
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

    @Override
    public void run() {
        int br = 0;
        while(true) {
            if(br < 52) {
            final int x = br;
            String putanja = stringList.remove(0);
            File file = new File(putanja);
            stringList.add(putanja);
            Image image = new Image(file.toURI().toString());
            Platform.runLater(()->imageView.setImage(image));
            String tmp = meaningOfCardForLabel(putanja);
            Platform.runLater(()->meaningOfCard.setText(tmp));

            br++;
            try{
                sleep(2000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            } }
            else br = 0;
        }


    }

    public String meaningOfCardForLabel(String putanja) {
        if(putanja.equals("karte/1.png"))
            return "Figura prelazi jedno polje.";
        else if(putanja.equals("karte/2.png"))
            return "Figrua prelazi dva polja.";
        else if(putanja.equals("karte/3.png"))
            return "Figura prelazi tri polja.";
        else if(putanja.equals("karte/4.png"))
            return "Figura prelazi cetiri polja.";
        else
            return "Figura prelazi n polja";
    }

}
