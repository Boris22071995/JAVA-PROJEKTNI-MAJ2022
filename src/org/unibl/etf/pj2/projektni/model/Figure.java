package org.unibl.etf.pj2.projektni.model;

public class Figure extends Thread{

    String colour;

    public Figure() {
        super();
    }
    public Figure(String colour) {
        this.colour = colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
    public String getColour() {
        return this.colour;
    }
}
