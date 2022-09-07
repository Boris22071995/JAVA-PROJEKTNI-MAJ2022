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
    List<Player> listOfPlayers = new ArrayList<>();
    Player player;
    public Holes(int numberOfHoles, MovingPath mp, List<Pane> paneList) {
        this.numberOfHoles = numberOfHoles;
        this.mp = mp;
        this.paneList = paneList;
    }
    public void addPlayer(Player player) {
        if(!listOfPlayers.contains(player)) listOfPlayers.add(player);

    }

    public boolean checkPosition(int temp) {
        boolean flag = false;
        for(int i = 0; i < listOfHoles.size(); i++) {
            if(listOfHoles.get(i) == mp.getPaneList().get(temp)) {
                flag = true;
            }
        }
        return flag;

    }
    public void processHoles() {
        int temp = 0;
        while(listOfHoles.size()!=numberOfHoles) {
            temp = rand.nextInt(mp.getPaneList().size());
            if(!checkPosition(temp)) {
                listOfHoles.add(mp.getPaneList().get(temp));
            }

        }

        for(int i = 0; i < listOfHoles.size(); i++)
          listOfHoles.get(i).setStyle("-fx-background-color: black;");
            int num = potm.getFiguresOnMap().size();
          for(int j = 0; j < num; j++) {
              Figure f = potm.getFigureFromMap(0);
              for(int i = 0; i < listOfPlayers.size(); i++) {
                  List<Figure> listTemp = listOfPlayers.get(i).getFigure();
                  for(int k = 0; k < 4; k++) {
                      if(f == listTemp.get(k)){
                          this.player = listOfPlayers.get(i);
                      }
                  }
              }
              Pane position = potm.getPositionFromMap(0);
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
                                  potm.removeFromMap(player,f);
                                  potm.addOnMap(player,position,f);
                              }
                          }
                      }

                  }
              }


          }
          try{
              Thread.sleep(500);
          }catch (InterruptedException ie) {
              ie.printStackTrace();
          }
        for(int i = 0; i < listOfHoles.size(); i++)
            listOfHoles.get(i).setStyle("-fx-border-color: black; -fx-background-color:rgba(0, 129, 255, 0.3)");
        System.out.println("OVDJE SMO U RUPI " + potm.getFiguresOnMap().size()); //
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
    public void setPositionOnTheMap(PositionOnTheMap position) {
        this.potm = position;
    }



}
