package org.unibl.etf.pj2.projektni.model;

import java.util.concurrent.SynchronousQueue;

public class PlayingDeckForGet {
    public SynchronousQueue<PlayingCard> cards = new SynchronousQueue<PlayingCard>();
    PlayingDeck playingDeck;
    public PlayingDeckForGet(PlayingDeck playingDeck) {
        this.playingDeck = playingDeck;
    }
    public synchronized void addCard() {
           PlayingCard pc = playingDeck.getCard();
           try {
               cards.offer(pc);
           } catch (Exception e) {
               e.printStackTrace();
           }
    }
    public PlayingCard readCard() {
        PlayingCard pc = null;
        try {
            pc = cards.take();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pc;
    }
}
