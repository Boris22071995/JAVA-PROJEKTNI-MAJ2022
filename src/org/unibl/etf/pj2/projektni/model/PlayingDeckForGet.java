package org.unibl.etf.pj2.projektni.model;

import org.unibl.etf.pj2.projektni.exception.LoggingException;
import java.util.concurrent.SynchronousQueue;
import java.util.logging.Level;

public class PlayingDeckForGet {
    public SynchronousQueue<PlayingCard> cards = new SynchronousQueue<>();
    PlayingDeck playingDeck;
    public PlayingDeckForGet(PlayingDeck playingDeck) {
        this.playingDeck = playingDeck;
    }
    public void addCard() {
               PlayingCard pc = playingDeck.getCard();
               cards.offer(pc);
    }
    public PlayingCard readCard() {
        PlayingCard pc = null;
        try {
            pc = cards.take();
        }catch (InterruptedException e) {
            LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }
        return pc;
    }
}
