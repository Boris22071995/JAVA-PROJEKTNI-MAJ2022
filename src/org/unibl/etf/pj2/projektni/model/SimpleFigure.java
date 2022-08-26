package org.unibl.etf.pj2.projektni.model;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;

public class SimpleFigure extends Figure implements MovingWay {

    Circle circle = new Circle();
    List<Pane> paneList;
    int matrixDimension;
    MovingPath mp;
    boolean done = false;

    public SimpleFigure(String boja, Pane[][] panes, int matrixDimension) {
        super(boja,panes);
        this.startSpot = 0;
        this.matrixDimension = matrixDimension;
        this.circle.setRadius(10);
     //   circle.setCenterX(20);
     //   circle.setCenterY(20);
        circle.setLayoutX(21);
        circle.setLayoutY(21);
        switch (boja) {
            case "crvena" -> this.circle.setFill(Color.RED);
            case "plava" -> this.circle.setFill(Color.BLUE);
            case "zelena" -> this.circle.setFill(Color.GREEN);
            default -> circle.setFill(Color.YELLOW);
        }
        this.mp = new MovingPath(orginalPanes, matrixDimension);
        if(matrixDimension % 2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();
        paneList = mp.getPaneList();
    }
    @Override
    public String move() {
        return "Obicna figura";
    }
    public Pane[][] getOrginalPanes(){return this.orginalPanes;}
    public List<Pane> getPaneList() {
        return this.paneList;
    }
    public Circle getCircle() {
        return this.circle;
    }
}
