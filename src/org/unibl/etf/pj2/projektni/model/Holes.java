package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Holes {
    int numberOfHoles;
    PositionOnTheMap potm;
    MovingPath mp;
    Random rand = new Random();
    List<Pane> listOfHoles = new ArrayList<>();
    List<Pane> paneList;
    Player player;
    public Holes(int numberOfHoles, PositionOnTheMap potm, MovingPath mp, List<Pane> paneList, Player player) {
        this.numberOfHoles = numberOfHoles;
        this.potm = potm;
        this.mp = mp;
        this.paneList = paneList;
        this.player = player;
    }

    public void processHoles() {
        int temp = 0;
        for(int i = 0; i < numberOfHoles; i++) {
            temp = rand.nextInt(mp.getPaneList().size());
            listOfHoles.add(mp.getPaneList().get(temp));
        }
        for(int i = 0; i < listOfHoles.size(); i++)
          listOfHoles.get(i).setStyle("-fx-background-color: black;");
          for(int j = 0; j < potm.getFiguresOnMap().size(); j++) {
              Figure f = potm.getFigureFromMap(j);
              Pane position = potm.getPositionFromMap(j);
              for(int i = 0; i < listOfHoles.size(); i++) {
                  if(listOfHoles.get(i) == position) {
                      if("Obicna figura".equals(f.move())){
                          SimpleFigure sf = (SimpleFigure) f;
                          for(int z = 0; z <paneList.size(); z++){
                              if(paneList.get(z) == position){
                                  final int x = z;
                                  Platform.runLater(()->paneList.get(x).getChildren().remove(sf.getCircle()));
                                  potm.removeFromMap(player,f);
                                  f.isDone = true;
                              }
                          }
                      }
                      else if("Super brza figura".equals(f.move())){
                          SuperSpeedFigure ssf = (SuperSpeedFigure) f;
                          for(int z = 0; z <paneList.size(); z++){
                              if(paneList.get(z) == position){
                                  final int x = z;
                                  Platform.runLater(()->paneList.get(x).getChildren().remove(ssf.getRectangle()));
                                  potm.removeFromMap(player,f);
                                  f.isDone = true;
                              }
                          }
                      }
                      else {
                          FlyingFigure ff = (FlyingFigure) f;
                          for(int z = 0; z <paneList.size(); z++){
                              if(paneList.get(z) == position){
                                  final int x = z;
                                  Platform.runLater(()->paneList.get(x).getChildren().remove(ff.getTriangle()));

                              }
                          }
                      }

                  }
              }


          }
          try{
              Thread.sleep(1000);
          }catch (InterruptedException ie) {
              ie.printStackTrace();
          }
        for(int i = 0; i < listOfHoles.size(); i++)
            listOfHoles.get(i).setStyle("-fx-border-color: black; -fx-background-color:rgba(0, 129, 255, 0.3)");
        for(int i = 0; i < potm.getFiguresOnMap().size(); i++) {
            Figure figure = potm.getFigureFromMap(i);
            Pane position2 = potm.getPositionFromMap(i);
            if("Lebdeca figura".equals(figure.move())){
                FlyingFigure ff = (FlyingFigure)figure;
                for(int j = 0; j < paneList.size(); j++ ) {
                    if(paneList.get(j) == position2) {
                        for(int z = 0; z < listOfHoles.size(); z++){
                            if(listOfHoles.get(z) == position2){
                        final int x = j;
                        Platform.runLater(() -> paneList.get(x).getChildren().add(ff.getTriangle()));}
                    }}
                }
            }
        }

          /*
             for(int i = 0; i < listOfHoles.size(); i++)
                  listOfHoles.get(i).setStyle("-fx-border-color: black; -fx-background-color:rgba(0, 129, 255, 0.3)");
              for(int i = 0; i < potm.getFiguresOnMap().size(); i++) {
                  Figure figure = potm.getFigureFromMap(i);
                  Pane position2 = potm.getPositionFromMap(i);
                  if("Lebdeca figura".equals(f.move())){
                      FlyingFigure ff = (FlyingFigure)f;
                      for(int j = 0; j < paneList.size(); j++ ) {
                          if(paneList.get(j) == position2) {
                              final int x = j;
                              Platform.runLater(() -> paneList.get(x).getChildren().add(ff.getCircle()));
                          }
                      }
                  }
              }

           */
    }



}
