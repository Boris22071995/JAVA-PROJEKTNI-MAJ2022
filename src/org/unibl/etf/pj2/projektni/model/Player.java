package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;
import java.util.Random;

public class Player {

    String name;
    String colour;
    Figure[] figure = new Figure[4];
    Pane[][] orginalPane;
    int matrixDimension;

    public Player(String name, String colour, Pane[][] panes, int matrixDimension) {
        this.name = name;
        this.colour = colour;
        this.orginalPane = panes;
        this.matrixDimension = matrixDimension;
        dodajFigure();
    }
    public Figure[] getFigure() {
        return figure;
    }
    public void setFigure(Figure[] figure) {
        this.figure = figure;
    }
    public void dodajFigure() {
        Random rand = new Random();
        for(int i = 0; i < 4; i++) {
            int br = rand.nextInt(3);
            if(br == 0) {
                SimpleFigure of = new SimpleFigure(colour, orginalPane, matrixDimension);
                figure[i]  = of;
            }
            else if(br == 1) {
                FlyingFigure lf = new FlyingFigure(colour, orginalPane, matrixDimension);
                figure[i] = lf;
            }
            else {
                SuperSpeedFigure sbf = new SuperSpeedFigure(colour, orginalPane, matrixDimension);
                figure[i] = sbf;
            }
        }
    }

}
