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
        switch (boja) {
            case "crvena" -> triangle.setFill(Color.RED);
            case "plava" -> triangle.setFill(Color.BLUE);
            case "zelena" -> triangle.setFill(Color.GREEN);
            default -> triangle.setFill(Color.YELLOW);
        }
         this.mp = new MovingPath(orginalPanes, matrixDimension);
        if(matrixDimension % 2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();
        paneList = mp.getPaneList();
    }
    @Override
    public String move() {
        return "Lebdeca figura";
    }
 /*   @Override
    public void run() {
        MovingPath mp = new MovingPath(orginalPanes, matrixDimension);
               if(matrixDimension % 2 == 0) mp.addToListEvenNumber();
               else mp.addToListOddNumber();
               paneList = mp.getPaneList();
                for(int i = 0; i < paneList.size(); i++) {
                    final int x = i;
                    Platform.runLater(() -> paneList.get(x).getChildren().add(triangle));
                    try{
                        sleep(1000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
    }*/
 @Override
 public synchronized void run() {
     while(startSpot < endSpot) {
         final int x = startSpot;
         Platform.runLater(()->paneList.get(x).getChildren().add(triangle));
         try{
             sleep(1000);
         }catch (InterruptedException e) {
             e.printStackTrace();
         }
         startSpot++;
     }
     try {
         this.wait();
     } catch (InterruptedException e) {
         e.printStackTrace();
     }

 /*    for(int i = startSpot; i < endSpot; i++) {
         final int x = i;
         Platform.runLater(()->paneList.get(x).getChildren().add(triangle));
         try{
             sleep(1000);
         }catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
     startSpot = endSpot + 1;
     this.setIsDone(true);*/
 }
    public Pane[][] getOrginalPanes(){return this.orginalPanes;}
}
