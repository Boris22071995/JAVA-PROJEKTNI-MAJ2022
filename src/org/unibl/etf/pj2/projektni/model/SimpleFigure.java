package org.unibl.etf.pj2.projektni.model;
import javafx.application.Platform;
import javafx.geometry.Pos;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.List;

public class SimpleFigure extends Figure implements MovingWay {

    Circle circle = new Circle();
    List<Pane> paneList;
    int matrixDimension;
    MovingPath mp;
    boolean done = false;
    PositionOnTheMap potm;
    boolean flag = false;
    GhostFigure ghostFigure;

    public SimpleFigure(String boja, Pane[][] panes, int matrixDimension, MovingPath mp, PositionOnTheMap potm, GhostFigure ghost) {
        super(boja,panes);
        this.startSpot = 0;
        this.matrixDimension = matrixDimension;
        createCircle();
        switch (boja) {
            case "crvena" -> this.circle.setFill(Color.RED);
            case "plava" -> this.circle.setFill(Color.BLUE);
            case "zelena" -> this.circle.setFill(Color.GREEN);
            default -> circle.setFill(Color.YELLOW);
        }
        this.mp = mp;
        paneList = mp.getPaneList();
        this.potm = potm;
        this.ghostFigure = ghost;
    }
    @Override
    public String move() {
        return "Obicna figura";
    }
    @Override
    public void drawFigure() {
        for(int i = getStartSpot(); i < getEndSpot(); i++) {
            final int x = i;
            if(x == getEndSpot() - 1) {
                if(potm.checkForAvalibalitiOfPosition(paneList.get(x)) == false) {
                    addProcessedPositions();
                    addPosition(paneList.get(x+1));
                    setBonusPositions(ghostFigure.checkForBonus(paneList.get(x + 1)));
                    Platform.runLater(()->paneList.get(x + 1).getChildren().add(getCircle()));
                    flag = true;
                }else {
                    Platform.runLater(() -> paneList.get(x).getChildren().add(getCircle()));
                    addProcessedPositions();
                    setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                    addPosition(paneList.get(x));
                }
            }else {
                if(potm.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                    setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                    addProcessedPositions();
                    addPosition(paneList.get(x));
                    Platform.runLater(()->paneList.get(x).getChildren().add(getCircle()));
                }
            }
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(flag == true) {
            setEndSpot(getEndSpot() + 1);
            flag = false;
        }

        if(getEndSpot() >= paneList.size()){
            final int x = getEndSpot() - 1;
            Platform.runLater(()->paneList.get(x).getChildren().remove(getCircle()));
            isDone = true;
        }
    }

    public Pane[][] getOrginalPanes(){return this.orginalPanes;}
    public List<Pane> getPaneList() {
        return this.paneList;
    }
    public Circle getCircle() {
        return this.circle;
    }
    public void createCircle() {
        if(matrixDimension == 7 || matrixDimension == 8) {
            circle.setRadius(14);
            circle.setLayoutX(21);
            circle.setLayoutY(21);
        }else if(matrixDimension == 9) {
            circle.setRadius(12);
            circle.setLayoutX(19);
            circle.setLayoutY(19);

        }else {
            circle.setRadius(10);
            circle.setLayoutX(17);
            circle.setLayoutY(17);
        }


    }

}
