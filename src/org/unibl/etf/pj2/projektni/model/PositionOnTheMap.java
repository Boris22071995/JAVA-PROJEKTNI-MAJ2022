package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class PositionOnTheMap {
    List<FigureOnMap> figuresOnMap = new ArrayList<>();

    public PositionOnTheMap() {
        super();
    }
    public List<FigureOnMap> getFiguresOnMap() {
        return this.figuresOnMap;
    }
    public void addOnMap(Player player, Pane position, Figure figure) {
            FigureOnMap fom = new FigureOnMap(player,position,figure);
            figuresOnMap.add(fom);
    }
    public void removeFromMap(Player player, Figure figure) {
        if(figuresOnMap.size()!=0) {
        for(int i = 0; i < figuresOnMap.size(); i++)
            if(figuresOnMap.get(i).figure==figure && figuresOnMap.get(i).player==player){
                figuresOnMap.remove(i);
            }}
    }
    public boolean checkForAvalibalitiOfPosition(Pane position) {
        if(figuresOnMap.size()!=0) {
            for (FigureOnMap figureOnMap : figuresOnMap)
                if (figureOnMap.position == position)
                    return false;
        }
        return true;

    }
    public Figure getFigureFromMap(int position) {
        return figuresOnMap.get(position).getFigure();
    }
    public Pane getPositionFromMap(int position) {
        return figuresOnMap.get(position).getPosition();
    }
    private static class FigureOnMap {
        Player player;
        Pane position;
        Figure figure;
        public FigureOnMap(Player player, Pane pane, Figure figure ) {
            this.player = player;
            this.position = pane;
            this.figure = figure;
        }
        public Figure getFigure() {
            return this.figure;
        }
        public Pane getPosition() {
            return this.position;
        }
    }
}

