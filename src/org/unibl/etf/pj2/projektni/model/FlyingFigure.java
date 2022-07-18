package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.unibl.etf.pj2.projektni.interfaces.MovingWay;
import java.util.ArrayList;
import java.util.List;

public class FlyingFigure extends Figure implements MovingWay {
    Polygon triangle;
  //  Pane[][] orginalPanes;
    int matrixDimension;
    List<Pane> paneList = new ArrayList<>();

    public FlyingFigure(String boja, Pane[][] panes, int matrixDimension) {
        super(boja, panes);
        this.matrixDimension = matrixDimension;
      //  orginalPanes = panes;
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
    }
    @Override
    public String move() {
        return "Lebdeca figura";
    }
    @Override
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
    }
    public Pane[][] getOrginalPanes(){return this.orginalPanes;}

}