package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;
import java.util.ArrayList;
import java.util.List;

public class SuperSpeedFigure extends Figure implements MovingWay {
    Rectangle rectangle = new Rectangle();
    List<Pane> paneList;
    int matrixDimension;
    MovingPath mp;
    PositionOnTheMap potm;
    GhostFigure ghostFigure;
    boolean flag = false;

    public SuperSpeedFigure(String boja, Pane[][] panes, int matrixDimension, MovingPath mp, PositionOnTheMap potm, GhostFigure ghost) {
        super(boja,panes);
        this.startSpot = 0;
        this.matrixDimension = matrixDimension;
        createRectangle();
        switch (boja) {
            case "crvena" -> rectangle.setFill(Color.RED);
            case "plava" -> rectangle.setFill(Color.BLUE);
            case "zelena" -> rectangle.setFill(Color.GREEN);
            default -> rectangle.setFill(Color.YELLOW);
        }
        this.mp = mp;
        paneList = mp.getPaneList();
        this.potm = potm;
        this.ghostFigure = ghost;

    }
    @Override
    public String move() {
        return "Super brza figura";
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
                    Platform.runLater(()->paneList.get(x + 1).getChildren().add(getRectangle()));
                    flag = true;
                }else {
                    addProcessedPositions();
                    setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                    addPosition(paneList.get(x));
                    Platform.runLater(() -> paneList.get(x).getChildren().add(getRectangle()));
                }
            }else {
                if(potm.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                    setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                    addProcessedPositions();
                    addPosition(paneList.get(x));
                    Platform.runLater(()->paneList.get(x).getChildren().add(getRectangle()));
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
            Platform.runLater(()->paneList.get(x).getChildren().remove(getRectangle()));
            isDone = true;
        }

    }

    public Pane[][] getOrginalPanes(){return this.orginalPanes;}
    public Rectangle getRectangle() {
        return this.rectangle;
    }
    public void createRectangle() {
        if(matrixDimension == 7 || matrixDimension == 8) {
            rectangle.setY(9);
            rectangle.setX(9);
            rectangle.setWidth(24);
            rectangle.setHeight(24);
        }else if(matrixDimension == 9) {
            rectangle.setY(8);
            rectangle.setX(8);
            rectangle.setWidth(22);
            rectangle.setHeight(22);
        }else {
            rectangle.setY(7);
            rectangle.setX(7);
            rectangle.setWidth(20);
            rectangle.setHeight(20);
        }
    }
}
