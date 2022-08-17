package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Thread{

    static public final AtomicInteger indexToPrint = new AtomicInteger(0);
    static private int threadNumber = 0;
    final private int index;
    static int numberOfFiguresThatEnd = 0;

    String name;
    String colour;
    int stop = 0;
    List<Figure> figure = new ArrayList<>();
    Pane[][] orginalPane;
    int matrixDimension;
    MovingPath mp;
    List<Pane> paneList;

    public Player(String name, String colour, Pane[][] panes, int matrixDimension) {
        this.name = name;
        this.colour = colour;
        this.orginalPane = panes;
        this.matrixDimension = matrixDimension;
        index = threadNumber++;
        dodajFigure();
        this.mp = new MovingPath(orginalPane, matrixDimension);
        if(matrixDimension % 2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();
        paneList = mp.getPaneList();

    }
    private int nextIndex() {
        return (index + 1) % threadNumber;
    }
    public List<Figure> getFigure() {
        return figure;
    }
    public void setFigure(List<Figure> figure) {
        this.figure = figure;
    }
    public void dodajFigure() {
        Random rand = new Random();
        for(int i = 0; i < 4; i++) {
            int br = rand.nextInt(3);
            if(br == 0) {
                SimpleFigure of = new SimpleFigure(colour, orginalPane, matrixDimension);
                figure.add(of);
            }
            else if(br == 1) {
                FlyingFigure lf = new FlyingFigure(colour, orginalPane, matrixDimension);
                figure.add(lf);
            }
            else {
                SuperSpeedFigure sbf = new SuperSpeedFigure(colour, orginalPane, matrixDimension);
                figure.add(sbf);
            }
        }
    }
    public String getNames() {
        return name;
    }
    public String getColour() {return colour;}
    @Override
    public void run() {
        Figure f = figure.remove(0);
        while(true) {
            synchronized (indexToPrint) {
                while(indexToPrint.get()!=index){
                    try {
                        indexToPrint.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(f.getStartSpot()!=paneList.size()) {
                if("Obicna figura".equals(f.move())) {
                    SimpleFigure sf = (SimpleFigure) f;
                    for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                        final int x = i;
                        Platform.runLater(()->paneList.get(x).getChildren().add(sf.getCircle()));
                        try{
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try{
                        f.setStartSpot(f.getEndSpot());
                        f.setEndSpot(f.getEndSpot()+4);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if("Lebdeca figura".equals(f.move())) {
                    FlyingFigure ff = (FlyingFigure) f;
                    for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                        final int x = i;
                        Platform.runLater(()->paneList.get(x).getChildren().add(ff.getTriangle()));
                        try{
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try{
                        f.setStartSpot(f.getEndSpot());
                        f.setEndSpot(f.getEndSpot()+4);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if("Super brza figura".equals(f.move())) {
                    SuperSpeedFigure ssf = (SuperSpeedFigure) f;
                    for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                        final int x = i;
                        Platform.runLater(()->paneList.get(x).getChildren().add(ssf.getRectangle()));
                        try{
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try{
                        f.setStartSpot(f.getEndSpot());
                        f.setEndSpot(f.getEndSpot()+4);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }}
                else{
                    numberOfFiguresThatEnd++;
                }
                if(numberOfFiguresThatEnd==16) {
                    System.out.println("KRAJ IGRE!");
                }
              //  System.out.println(name+" " + f.move());

                figure.add(f);
                f=figure.remove(0);
                indexToPrint.set(nextIndex());
                       indexToPrint.notifyAll();

            }
        }
    }

}
