package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Holes {
    int numberOfHoles;
    List<Figure> figuresToRemove = new ArrayList<>();
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
        for (Pane listOfHole : listOfHoles) {
            if (listOfHole == mp.getPaneList().get(temp)) {
                flag = true;
            }
        }
        return flag;
    }
    public void processHoles() {
        int temp;
        List<Figure> listOfFlyingFigure = new ArrayList<>();
        listOfHoles.clear();
        while(listOfHoles.size()!=numberOfHoles) {
            temp = rand.nextInt(mp.getPaneList().size() - 1);
            if(!checkPosition(temp)) {
                listOfHoles.add(mp.getPaneList().get(temp));
            }
        }

        for (Pane listOfHole : listOfHoles) listOfHole.setStyle("-fx-background-color: black;");

            int num = potm.getFiguresOnMap().size();
          for(int j = 0; j < num; j++) {
              Figure f = potm.getFigureFromMap(j);
              for (Player listOfPlayer : listOfPlayers) {
                  List<Figure> listTemp = listOfPlayer.getFigure();
                  for (int k = 0; k < 4; k++) {
                      if (f == listTemp.get(k)) {
                          this.player = listOfPlayer;
                      }
                  }
              }
              Pane position = potm.getPositionFromMap(j);
              for (Pane listOfHole : listOfHoles) {
                  if (listOfHole == position) {
                      if ("Obicna figura".equals(f.move())) {
                          SimpleFigure sf = (SimpleFigure) f;
                          for (int z = 0; z < paneList.size(); z++) {
                              if (paneList.get(z) == position) {
                                  final int x = z;
                                  Platform.runLater(() -> paneList.get(x).getChildren().remove(sf.getCircle()));
                                  figuresToRemove.add(f);
                                  f.setTimeOfStop();
                                  f.isDone = true;
                              }
                          }
                      } else if ("Super brza figura".equals(f.move())) {
                          SuperSpeedFigure ssf = (SuperSpeedFigure) f;
                          for (int z = 0; z < paneList.size(); z++) {
                              if (paneList.get(z) == position) {
                                  final int x = z;
                                  Platform.runLater(() -> paneList.get(x).getChildren().remove(ssf.getRectangle()));
                                  figuresToRemove.add(f);
                                  f.setTimeOfStop();
                                  f.isDone = true;
                              }
                          }
                      } else {
                          FlyingFigure ff = (FlyingFigure) f;
                          for (int z = 0; z < paneList.size(); z++) {
                              if (paneList.get(z) == position) {
                                  final int x = z;
                                  Platform.runLater(() -> paneList.get(x).getChildren().remove(ff.getTriangle()));
                                  listOfFlyingFigure.add(f);
                              }
                          }
                      }
                  }
              }
          }

        for (Figure f : figuresToRemove) {
            Player temporaryPlayer = null;
            for (Player listOfPlayer : listOfPlayers) {
                List<Figure> temporaryListOfFigure = listOfPlayer.getFigure();
                for (int z = 0; z < 4; z++) {
                    if (temporaryListOfFigure.get(z) == f) temporaryPlayer = listOfPlayer;
                }
            }
            potm.removeFromMap(temporaryPlayer, f);
        }
          try{
              Thread.sleep(500);
          }catch (InterruptedException ie) {
              ie.printStackTrace();
          }
        for (Pane listOfHole : listOfHoles)
            listOfHole.setStyle("-fx-border-color: black; -fx-background-color:rgba(0, 129, 255, 0.3)");
        for(int i = 0; i < potm.getFiguresOnMap().size(); i++) {
            Figure figure = potm.getFigureFromMap(i);
            Pane position2 = potm.getPositionFromMap(i);
            if("Lebdeca figura".equals(figure.move())){
                FlyingFigure ff = (FlyingFigure)figure;
                for (Pane paneHole : listOfHoles) {
                    if (position2 == paneHole) {
                        for (int z = 0; z < paneList.size(); z++) {
                            if (paneList.get(z) == position2) {
                                final int x = z;
                                Platform.runLater(() -> paneList.get(x).getChildren().add(ff.getTriangle()));
                            }
                        }
                    }
                }
            }
        }
    }
    public void setPositionOnTheMap(PositionOnTheMap position) {
        this.potm = position;
    }
}
