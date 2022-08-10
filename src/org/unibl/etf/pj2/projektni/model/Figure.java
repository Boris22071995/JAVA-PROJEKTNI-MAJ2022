package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;

public abstract class Figure extends Thread implements MovingWay {

    String colour;
    Pane[][] orginalPanes;
    int startSpot;
    int endSpot;
    int count;
    Boolean isDone = false;

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
    public void setStartSpot(int startSpot) {
        this.startSpot = startSpot;
    }
    public int getStartSpot() {
        return this.startSpot;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return this.count;
    }
    public void setEndSpot(int endSpot) {
        this.endSpot = endSpot;
    }
    public int getEndSpot() {
        return this.endSpot;
    }
    @Override
    public String move() {
        return "Figura";
    }
    @Override
    public abstract void run();
    public void setIsDone(Boolean value) {
        isDone = value;
    }
    public Boolean getIsDone() {
        return isDone;
    }
}
