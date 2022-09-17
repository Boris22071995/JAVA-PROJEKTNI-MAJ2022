package org.unibl.etf.pj2.projektni.model;

import org.unibl.etf.pj2.projektni.exception.LoggingException;

import java.util.logging.Level;

public class Producer extends Thread{
        PlayingDeckForGet pd;

        public Producer(PlayingDeckForGet pd) {
            this.pd = pd;
        }

        @Override
        public synchronized void run() {
            while (true) {
                try {
                    pd.addCard();
                    sleep(1600);
                }catch (InterruptedException e) {
                    LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                }
            }
        }
}
