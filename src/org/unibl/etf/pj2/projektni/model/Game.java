package org.unibl.etf.pj2.projektni.model;

import java.util.ArrayList;
import java.util.List;

public class Game extends Thread{
    List<Figure> allFigures = new ArrayList<>();

    public Game() {
        super();
    }
    public Game(List<Figure> allFigures) {
        this.allFigures = allFigures;
    }

    @Override
    public synchronized  void run() {
        for(int i = 0; i < allFigures.size(); i++){
            allFigures.get(i).setStartSpot(0);
            allFigures.get(i).setEndSpot(1);
            allFigures.get(i).start();
        }
        while (allFigures.size() != 0) {
            Figure figure = allFigures.remove(0);
            try
            {
                figure.setStartSpot(figure.getEndSpot()+1);
                figure.setEndSpot(figure.getEndSpot() + 4);
                allFigures.add(figure);
                System.out.println(figure.move());
                this.notify();
                sleep(5000);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
