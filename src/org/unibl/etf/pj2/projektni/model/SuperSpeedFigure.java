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
        rectangle.setY(10);
        rectangle.setX(10);
        rectangle.setWidth(20);
        rectangle.setHeight(20);
        switch (boja) {
            case "crvena" -> rectangle.setFill(Color.RED);
            case "plava" -> rectangle.setFill(Color.BLUE);
            case "zelena" -> rectangle.setFill(Color.GREEN);
            default -> rectangle.setFill(Color.YELLOW);
        }
         this.mp = new MovingPath(orginalPanes, matrixDimension);
        if(matrixDimension %2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();
        paneList = mp.getPaneList();
    }
    @Override
    public String move() {
        return "Super brza figura";
    }
  /*  @Override
    public void run() {
        MovingPath mp = new MovingPath(orginalPanes, matrixDimension);
        if(matrixDimension %2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();
        paneList = mp.getPaneList();
        for(int i = 0; i < paneList.size(); i++) {
            final int x = i;
            Platform.runLater(() -> paneList.get(x).getChildren().add(rectangle));
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
          Platform.runLater(()->paneList.get(x).getChildren().add(rectangle));
          try{
              sleep(1000);
          }catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
      startSpot = endSpot + 1;
      this.setIsDone(true);
  }
    public Pane[][] getOrginalPanes(){return this.orginalPanes;}
}
