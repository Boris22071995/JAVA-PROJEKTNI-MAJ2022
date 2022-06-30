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
    List<Pane> paneList = new ArrayList<>();
    int matrixDimension;
    Pane[][] orginalPanes;

    public SimpleFigure(String boja, Pane[][] panes, int matrixDimension) {
        super(boja);
        this.matrixDimension = matrixDimension;
        orginalPanes = panes;
        this.circle.setRadius(10);
        circle.setCenterX(20);
        circle.setCenterY(20);
        switch (boja) {
            case "crvena" -> this.circle.setFill(Color.RED);
            case "plava" -> this.circle.setFill(Color.BLUE);
            case "zelena" -> this.circle.setFill(Color.GREEN);
            default -> circle.setFill(Color.YELLOW);
        }
    }
    @Override
    public String move() {
        return "Obicna figura";
    }
    @Override
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
    }
}
