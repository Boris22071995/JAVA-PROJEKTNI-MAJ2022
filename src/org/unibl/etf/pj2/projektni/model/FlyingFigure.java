package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;

import java.util.List;

public class FlyingFigure extends Figure implements MovingWay {
    Polygon triangle;
    int matrixDimension;
    List<Pane> paneList;
    MovingPath mp;

    public FlyingFigure(String boja, Pane[][] panes, int matrixDimension) {
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

    }
    @Override
    public String move() {
        return "Lebdeca figura";
    }
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
