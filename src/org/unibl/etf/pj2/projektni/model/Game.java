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
    public void run() {

        while (allFigures.size() != 0) {
            Figure figure = allFigures.get(0);
            allFigures.remove(0).start();
            figure.setStartSpot(figure.getEndSpot() + 1);
            figure.setEndSpot(figure.endSpot + 4);
            allFigures.add(figure);
            try
            {
                sleep(5000);
                figure.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
