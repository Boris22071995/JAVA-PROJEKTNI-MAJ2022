package org.unibl.etf.pj2.projektni.model;

public class Producer extends Thread{
        PlayingDeckForGet pd;

        public Producer(PlayingDeckForGet pd) {
            this.pd = pd;
        }

        public void run() {
            while (true) {
                try {
                    pd.addCard();
                    sleep(1000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}
