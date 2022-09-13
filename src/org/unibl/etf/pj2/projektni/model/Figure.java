package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure implements MovingWay {

    String colour;
    Pane[][] orginalPanes;
    int startSpot;
    int endSpot;
    int count;
    Boolean isDone = false;
    Boolean isActive = false;
    int bonusPositions = 0;
    int numberOfProcessedPositions = 0;
    List<Pane> processedPath = new ArrayList<>();

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
    public void setIsDone(Boolean value) {
        isDone = value;
    }
    public Boolean getIsDone() {
        return isDone;
    }
    public void setIsActive(Boolean value) {
        isActive = value;
    }
    public Boolean getIsActive(){
        return this.isActive;
    }
    public void setBonusPositions(int number) {
        this.bonusPositions += number;
    }
    public int getBonusPositions(){
        return this.bonusPositions;
    }
    public void resetBonusPositions() { this.bonusPositions = 0; }
    public void addProcessedPositions() {
        this.numberOfProcessedPositions++;
    }
    public int getNumberOfProcessedPositions() {
        return this.numberOfProcessedPositions;
    }
    public void addPosition(Pane pane) {
        processedPath.add(pane);
    }
    public List<Pane> getProcessedPath() {
        return this.processedPath;
    }
    public abstract void drawFigure();

}
