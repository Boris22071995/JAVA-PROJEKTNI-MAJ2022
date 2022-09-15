package org.unibl.etf.pj2.projektni.model;

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
                    sleep(1100);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}
