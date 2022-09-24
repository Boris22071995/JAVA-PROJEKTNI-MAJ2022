package org.unibl.etf.pj2.projektni.model;

import org.unibl.etf.pj2.projektni.exception.LoggingException;
import java.util.logging.Level;

public class Producer extends Thread{
        PlayingDeckForGet pd;

        public Producer(PlayingDeckForGet pd) {
            this.pd = pd;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    pd.addCard();
                    sleep(500);
                }catch (InterruptedException e) {
                    LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                }
            }
        }
}
