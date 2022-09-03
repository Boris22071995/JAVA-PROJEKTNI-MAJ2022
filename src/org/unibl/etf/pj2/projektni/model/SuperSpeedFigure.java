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

    public SuperSpeedFigure(String boja, Pane[][] panes, int matrixDimension) {
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

    }
    @Override
    public String move() {
        return "Super brza figura";
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
