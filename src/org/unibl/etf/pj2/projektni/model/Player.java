package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {

    String name;
    String colour;
    List<Figure> figure = new ArrayList<>();
    Pane[][] orginalPane;
    int matrixDimension;

    public Player(String name, String colour, Pane[][] panes, int matrixDimension) {
        this.name = name;
        this.colour = colour;
        this.orginalPane = panes;
        this.matrixDimension = matrixDimension;
        dodajFigure();
    }
    public List<Figure> getFigure() {
        return figure;
    }
    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }
    public void dodajFigure() {
        Random rand = new Random();
        for(int i = 0; i < 4; i++) {
            int br = rand.nextInt(3);
            if(br == 0) {
                SimpleFigure of = new SimpleFigure(colour, orginalPane, matrixDimension);
                figure.add(of);
            }
            else if(br == 1) {
                FlyingFigure lf = new FlyingFigure(colour, orginalPane, matrixDimension);
                figure.add(lf);
            }
            else {
                SuperSpeedFigure sbf = new SuperSpeedFigure(colour, orginalPane, matrixDimension);
                figure.add(sbf);
            }
        }
    }
    public String getName() {
        return name;
    }
    public String getColour() {return colour;}

}
