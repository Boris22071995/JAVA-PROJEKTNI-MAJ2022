package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
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
    PlayingDeck playingDeck;
    private final Consumer consumer;
    ImageView imageView;
    PositionOnTheMap positionOnTheMap;
    int numberOfFiguresThatAreDone = 1;
    boolean flag = false;


    public Player(String name, String colour, Pane[][] panes, int matrixDimension,PlayingDeck playingDeck,ImageView imageView, PositionOnTheMap positionOnTheMap) {
        this.imageView = imageView;
        this.positionOnTheMap = positionOnTheMap;
        this.playingDeck = playingDeck;
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


        PlayingDeckForGet pdfg = new PlayingDeckForGet(playingDeck);
        Producer producer = new Producer(pdfg);
        consumer = new Consumer(pdfg,imageView);
        producer.start();
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

           while(true){

               Figure f = figure.get(0);
               if (f.getIsDone()) {
                   Figure temp = figure.remove(0);
                   figure.add(temp);
                   f = figure.get(0);
                   numberOfFiguresThatAreDone++;
               }
               synchronized (indexToPrint) {
                   while(indexToPrint.get()!=index){
                       try {
                           indexToPrint.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                   if(numberOfFiguresThatAreDone <= 4){
                       System.out.println("BROJ FIGURA KOJE SU ZAVRSILE " + numberOfFiguresThatAreDone);
                   positionOnTheMap.removeFromMap(this,f);
                   PlayingCard pc = consumer.getCard();
                   int pomjeraj = pc.getNumber();
                   if("Obicna figura".equals(f.move())){
                       SimpleFigure sf = (SimpleFigure) f;
                       if(pomjeraj!=5) {
                           f.setStartSpot(f.getEndSpot());
                           if((f.getEndSpot() + pomjeraj) <= paneList.size()){
                           f.setEndSpot(f.getEndSpot() + pomjeraj);}
                           else f.setEndSpot(paneList.size());

                       for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x)) == false){
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(sf.getCircle()));
                                   flag = true;

                               }else {
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(sf.getCircle()));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   Platform.runLater(()->paneList.get(x).getChildren().add(sf.getCircle()));
                               }
                           }
                           try{
                               sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                       if(flag == true) {
                           f.setEndSpot(f.getEndSpot() + 1);
                           flag = false;
                       }

                       if(f.getEndSpot() >= paneList.size()){
                           final int x = f.getEndSpot() - 1;
                           Platform.runLater(()->paneList.get(x).getChildren().remove(sf.getCircle()));
                           f.isDone = true;
                       }
                           if(f.getEndSpot()<paneList.size())
                           positionOnTheMap.addOnMap(this,paneList.get(f.getEndSpot() - 1),f);

                   }else {
                       System.out.println("RUPEE");
                   }}
                   else if("Lebdeca figura".equals(f.move())) {
                       FlyingFigure ff = (FlyingFigure) f;
                       if(pomjeraj!=5) {
                           f.setStartSpot(f.getEndSpot());
                           if((f.getEndSpot() + pomjeraj) <= paneList.size()){
                               f.setEndSpot(f.getEndSpot() + pomjeraj);}
                           else f.setEndSpot(paneList.size());

                       for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== false){
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(ff.getTriangle()));
                                   flag = true;
                               }else {
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(ff.getTriangle()));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   Platform.runLater(()->paneList.get(x).getChildren().add(ff.getTriangle()));
                               }
                           }
                           try{
                               sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                           if(flag == true) {
                               f.setEndSpot(f.getEndSpot() + 1);
                               flag = false;
                           }
                       if(f.getEndSpot() >= paneList.size()){
                           final int x = f.getEndSpot() - 1;
                           Platform.runLater(()->paneList.get(x).getChildren().remove(ff.getTriangle()));
                           f.isDone = true;
                       }
                       if(f.getEndSpot()<paneList.size())
                           positionOnTheMap.addOnMap(this,paneList.get(f.getEndSpot() - 1),f);

                   }else {
                       System.out.println("RUPEE");
                   }}
                   else if("Super brza figura".equals(f.move())){
                       SuperSpeedFigure ssf = (SuperSpeedFigure) f;
                       if(pomjeraj!=5) {
                           f.setStartSpot(f.getEndSpot());
                           if((f.getEndSpot() + (pomjeraj * 2)) <= paneList.size()){
                               f.setEndSpot(f.getEndSpot() + (pomjeraj * 2));}
                           else f.setEndSpot(paneList.size());

                       for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== false){
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(ssf.getRectangle()));
                                   flag = true;
                               }else {
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(ssf.getRectangle()));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   Platform.runLater(()->paneList.get(x).getChildren().add(ssf.getRectangle()));
                               }
                           }
                           try{
                               sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }
                           if(flag == true) {
                               f.setEndSpot(f.getEndSpot() + 1);
                               flag = false;
                           }
                       if(f.getEndSpot() >= paneList.size()){
                           final int x = f.getEndSpot() - 1;
                           Platform.runLater(()->paneList.get(x).getChildren().remove(ssf.getRectangle()));
                           f.isDone = true;
                       }
                           if(f.getEndSpot()<paneList.size())
                           positionOnTheMap.addOnMap(this,paneList.get(f.getEndSpot() - 1),f);

                   }else {
                       System.out.println("RUPEE");
                   }}
                   indexToPrint.set(nextIndex());
                   indexToPrint.notifyAll();
               }
                   else{
                       System.out.println("SVE FIGURE ZA IGACA " + this.name + " ZAVRSILE");
                       indexToPrint.set(nextIndex());
                       indexToPrint.notifyAll();
                   }
           }
       }
   }

    public List<Pane> getPaneList() {
        return this.paneList;
    }
}
