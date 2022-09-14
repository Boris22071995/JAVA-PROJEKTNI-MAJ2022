package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GhostFigure extends Thread{
    List<Pane> path = new ArrayList<>();
    List<BonusPosition> positionsOfBonuses = new ArrayList<>();
    int numberOfPositions;
    int matrixDimension;
    int bonus;
    int position;
    public GhostFigure(List<Pane> path, int matrixDimension) {
        this.path = path;
        this.matrixDimension = matrixDimension;
    }
    @Override
    public void run() {
        while(true) {
            Random rand = new Random();
            bonus = rand.nextInt(matrixDimension - 2 + 1) + 2;
            position = rand.nextInt(path.size());
            int realPosition = getPositionforReal(position);
            if(positionsOfBonuses.size() <= path.size()) {
                positionsOfBonuses.add(new BonusPosition(path.get(realPosition), bonus));
            }
            try{
                System.out.println("BROJ BONUS POLJA JE " + positionsOfBonuses.size() + " POZICIJA " + positionsOfBonuses.get(0).getPosition() + " BOnUS + " + positionsOfBonuses.get(0).getBonus());
                sleep(5000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int checkForBonus(Pane pane) {
        int bonus = 0;
        for(int i = 0; i < positionsOfBonuses.size(); i++) {
            if(positionsOfBonuses.get(i).getPosition() == pane)
            {
                bonus = positionsOfBonuses.get(i).getBonus();
                positionsOfBonuses.remove(i);
            }
        }
        return bonus;
    }
    public int getPositionforReal(int position) {
        int temp = position;
            while (isPositionFree(temp++) != true && temp < path.size()) ;
        return temp;
    }
    public boolean isPositionFree(int position) {
        Pane pane = path.get(position);
        for(int i = 0; i < positionsOfBonuses.size(); i++) {
            if(positionsOfBonuses.get(i).getPosition() == pane)
                return false;
        }
        return true;
    }


    private static class BonusPosition {
        Pane position;
        int bonus;
        public BonusPosition() {
            super();
        }
        public BonusPosition(Pane position, int bonus) {
            this.bonus = bonus;
            this.position = position;
        }

        public Pane getPosition() {
            return position;
        }

        public void setPosition(Pane position) {
            this.position = position;
        }

        public int getBonus() {
            return bonus;
        }

        public void setBonus(int bonus) {
            this.bonus = bonus;
        }
    }

}
