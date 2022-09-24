package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.unibl.etf.pj2.projektni.exception.LoggingException;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;
import java.util.List;
import java.util.logging.Level;

public class FlyingFigure extends Figure implements MovingWay {
    Polygon triangle;
    int matrixDimension;
    List<Pane> paneList;
    MovingPath mp;
    PositionOnTheMap potm;
    GhostFigure ghostFigure;
    boolean flag = false;
    boolean isFigureisStarted = false;

    public FlyingFigure(String boja, Pane[][] panes, int matrixDimension, MovingPath mp, PositionOnTheMap potm, GhostFigure ghost) {
        super(boja, panes);
        this.startSpot = 0;
        this.matrixDimension = matrixDimension;
        this.triangle = new Polygon();
        this.triangle.getPoints()
                .setAll(25.0, 10.0,
                        35.0, 35.0,
                        15.0, 35.0);
        createTriangle();
        switch (boja) {
            case "crvena" -> triangle.setFill(Color.RED);
            case "plava" -> triangle.setFill(Color.BLUE);
            case "zelena" -> triangle.setFill(Color.GREEN);
            default -> triangle.setFill(Color.YELLOW);
        }
        this.mp = mp;
        paneList = mp.getPaneList();
        this.potm = potm;
        this.ghostFigure = ghost;

    }
    @Override
    public String move() {
        return "Lebdeca figura";
    }
    @Override
    public void drawFigure()  {
        synchronized (Player.indexToPrint) {
        for(int i = getStartSpot(); i < getEndSpot(); i++) {
            if(pause) {
                    try{
                        Player.indexToPrint.wait();
                    }catch (InterruptedException ie) {
                        LoggingException.logger.log(Level.SEVERE, ie.fillInStackTrace().toString());
                    }

                }
            if(!isFigureisStarted && getStartSpot() == 0) {
                this.setTimeOfStart();
                isFigureisStarted = true;
            }
            final int x = i;
            if (x == getEndSpot() - 1) {
                if (!potm.checkForAvalibalitiOfPosition(paneList.get(x))) {
                    addProcessedPositions();
                    addPosition(paneList.get(x));
                    addPosition(paneList.get(x + 1));
                    addTimeIngame();
                    setBonusPositions(ghostFigure.checkForBonus(paneList.get(x + 1)));
                    Platform.runLater(() -> paneList.get(x + 1).getChildren().add(getTriangle()));
                    flag = true;
                } else {
                    addProcessedPositions();
                    setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                    addPosition(paneList.get(x));
                    addTimeIngame();
                    Platform.runLater(() -> paneList.get(x).getChildren().add(getTriangle()));
                }
            } else {
                if (potm.checkForAvalibalitiOfPosition(paneList.get(x))) {
                    setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                    addProcessedPositions();
                    addPosition(paneList.get(x));
                    addTimeIngame();
                    Platform.runLater(() -> paneList.get(x).getChildren().add(getTriangle()));
                } else {
                    addProcessedPositions();
                    addTimeIngame();
                    addPosition(paneList.get(x));
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
            }
        }

        if(flag) {
            setEndSpot(getEndSpot() + 1);
            flag = false;
        }
        if(getEndSpot() >= paneList.size()){
            final int x = getEndSpot() - 1;
            Platform.runLater(()->paneList.get(x).getChildren().remove(getTriangle()));
            this.setTimeOfStop();
            isDone = true;
        }
    }}
    public Pane[][] getOrginalPanes(){return this.orginalPanes;}
    public Polygon getTriangle() {
        return this.triangle;
    }
    public void createTriangle() {
        if(matrixDimension == 7 || matrixDimension == 8) {
            triangle.setLayoutX(-4);
            triangle.setLayoutY(-4);
        }else if(matrixDimension == 9) {
            triangle.setLayoutX(-6);
            triangle.setLayoutY(-6);
        }else {
            triangle.setLayoutX(-8);
            triangle.setLayoutY(-8);
        }
    }
}
