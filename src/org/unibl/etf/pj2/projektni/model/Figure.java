package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;

public abstract class Figure extends Thread implements MovingWay {

    String colour;
    Pane[][] orginalPanes;

    public Figure() {
        super();
    }
    public Figure(String colour, Pane[][] panes) {
        this.colour = colour;
        this.orginalPanes = panes;
    }
    public void setColour(String colour) {
        this.colour = colour;
    }
    public String getColour() {
        return this.colour;
    }
    public Pane[][] getOrginalPanes() {return this.orginalPanes;}

    @Override
    public String move() {
        return "Figura";
    }
    @Override
    public abstract void run();
}
