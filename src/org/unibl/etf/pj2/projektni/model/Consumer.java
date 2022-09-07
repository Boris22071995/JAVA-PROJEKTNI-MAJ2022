package org.unibl.etf.pj2.projektni.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;

public class Consumer extends Thread{
    PlayingDeckForGet pd;
    ImageView imageView;
    public Consumer(PlayingDeckForGet pd, ImageView imageView) {
        this.pd = pd;
        this.imageView = imageView;
    }

    public synchronized PlayingCard getCard() {
        PlayingCard pc = pd.readCard();
        File file = new File(pc.getImagePath());
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        return pc;

    }
}
  /*
     File file = new File("karte/1.png");
        Image image = new Image(file.toURI().toString());
        //imageView = new ImageView(image);
        imageView.setImage(image);
     */
