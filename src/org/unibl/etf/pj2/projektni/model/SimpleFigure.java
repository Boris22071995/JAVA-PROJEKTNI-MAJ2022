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


    public SimpleFigure(String boja, Pane[][] panes, int matrixDimension) {
        super(boja,panes);
        this.startSpot = 0;
        this.matrixDimension = matrixDimension;
        this.circle.setRadius(10);
        circle.setCenterX(20);
        circle.setCenterY(20);
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
 /*   @Override
    public void run() {
        MovingPath mp = new MovingPath(orginalPanes, matrixDimension);
        if(matrixDimension % 2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();
        paneList = mp.getPaneList();
        for(int i = 0; i < paneList.size(); i++) {
            final int x = i;
            Platform.runLater(() -> paneList.get(x).getChildren().add(circle));
            try{
                sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

    @Override
    public synchronized void run() {
        for(int i = startSpot; i < endSpot; i++) {
            final int x = i;
            Platform.runLater(()->paneList.get(x).getChildren().add(circle));
            try{
                sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.setIsDone(true);
        startSpot = endSpot + 1;
    }
    public Pane[][] getOrginalPanes(){return this.orginalPanes;}
}
