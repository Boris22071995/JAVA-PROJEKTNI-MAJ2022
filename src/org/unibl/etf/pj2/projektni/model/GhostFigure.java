package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    boolean firstCircle = true;
    List<LabelForBonuses> labels = new ArrayList<>();
    public GhostFigure(List<Pane> path, int matrixDimension) {
        this.path = path;
        this.matrixDimension = matrixDimension;
    }
    @Override
    public void run() {
        while(true) {
            if(firstCircle == true) {
                try{
                  //  System.out.println("PRVI KRUG");
                    sleep(5000);
                    firstCircle = false;
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Random rand = new Random();
            bonus = rand.nextInt(matrixDimension - 2 + 1) + 2;
            position = rand.nextInt(path.size() - 2);
            int realPosition = getPositionforReal(position);
            if(realPosition == 0) {
                realPosition = getPositionforReal(realPosition);
            }
            int positionForBonus = realPosition;
            if(positionsOfBonuses.size() <= path.size()) {
                positionsOfBonuses.add(new BonusPosition(path.get(realPosition), bonus));
                labels.add(new LabelForBonuses(path.get(realPosition),bonus));
                LabelForBonuses temp = labels.get(labels.size()-1);
                Platform.runLater(()->{
                    path.get(positionForBonus).getChildren().add(temp.getLabel());
                });

            }
            try{
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
                LabelForBonuses lfb = null;
                for(int j = 0; j < labels.size(); j++) {
                    if(labels.get(j).getPosition() == pane) {
                        lfb = labels.remove(j);
                    }
                }
                final LabelForBonuses tmp = lfb;
                Platform.runLater(()->{
                    pane.getChildren().remove(tmp.getLabel());
                });
            }
        }
        return bonus;
    }
    public int getPositionforReal(int position) {
        int temp = position;
            while (isPositionFree(temp) != true && temp < path.size()){temp++;} ;
            if(temp<path.size()) {
                return temp;
            }else {
                temp = path.size() - 2;
                for(BonusPosition b : positionsOfBonuses) {
                    if (b.getPosition() == path.get(temp)){
                        temp = 0;
                    }
                }
                return temp;
            }

    }
    public boolean isPositionFree(int position) {
        if(position < path.size()) {
            Pane pane = path.get(position);
            for (int i = 0; i < positionsOfBonuses.size(); i++) {
                if (positionsOfBonuses.get(i).getPosition() == pane)
                    return false;
            }
            return true;
        }else return false;
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
    private static class LabelForBonuses {
        Label label;
        Pane position;
        int bonus;
        public LabelForBonuses(Pane position, int bonus) {
            this.position = position;
            this.bonus = bonus;
            this.label = new Label();
            label.setText("+"+bonus);
            label.setFont(Font.font("System", FontWeight.BOLD,10));
            label.setTextFill(Color.RED);
        }

        public Label getLabel() {
            return label;
        }

        public void setLabel(Label label) {
            this.label = label;
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
