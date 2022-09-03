package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
    int positionOfPlayer;
    GhostFigure ghostFigure;
    boolean isGhostStarted = false;
    ArrayList<Label> labels;
    List<Integer> numbers;
    List<Integer> processedNumbers;
    Label meaningOfCard;
    Label timeLabel;



    public Player(String name, String colour, Pane[][] panes, int matrixDimension,PlayingDeck playingDeck,ImageView imageView, PositionOnTheMap positionOnTheMap, int positionOfPlayer, GhostFigure ghostFigure, ArrayList<Label> labels, Label meaningOfCard, Label timeLabel) {
        this.timeLabel = timeLabel;
        this.meaningOfCard = meaningOfCard;
        this.labels = labels;
        this.positionOfPlayer = positionOfPlayer;
        this.imageView = imageView;
        this.positionOnTheMap = positionOnTheMap;
        this.playingDeck = playingDeck;
        this.name = name;
        this.colour = colour;
        this.orginalPane = panes;
        this.matrixDimension = matrixDimension;
        index = threadNumber++;
        dodajFigure();
        this.mp = new MovingPath(orginalPane, matrixDimension, labels);
        if(matrixDimension % 2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();
        paneList = mp.getPaneList();
        this.numbers = mp.getNumbers();
        this.ghostFigure = ghostFigure;
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
   public synchronized void run() {
            if(positionOfPlayer == 1 && isGhostStarted == false) {
                ghostFigure.start();
                isGhostStarted = true;
                MyTimer myTimer = new MyTimer(this.timeLabel);
                myTimer.start();
            }
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

                   PlayingCard pc = consumer.getCard();
                   int pomjeraj = pc.getNumber();
                   if("Obicna figura".equals(f.move())){

                       SimpleFigure sf = (SimpleFigure) f;
                       if(pomjeraj!=5) {
                           positionOnTheMap.removeFromMap(this,f);
                           f.setStartSpot(f.getEndSpot());
                           if((f.getEndSpot() + pomjeraj + f.getBonusPositions()) <= paneList.size()){
                           f.setEndSpot(f.getEndSpot() + pomjeraj + f.getBonusPositions());}

                           else f.setEndSpot(paneList.size());
                           Platform.runLater(()->meaningOfCard.setText(this.toString()));
                            f.resetBonusPositions();
                       for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x)) == false){
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x + 1)));
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(sf.getCircle()));
                                   flag = true;

                               }else {
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(sf.getCircle()));
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
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
                           Holes holes = new Holes(25,positionOnTheMap,mp,paneList,this);
                           holes.processHoles();
                   }}
                   else if("Lebdeca figura".equals(f.move())) {
                       FlyingFigure ff = (FlyingFigure) f;
                       if(pomjeraj!=5) {
                           positionOnTheMap.removeFromMap(this,f);
                           f.setStartSpot(f.getEndSpot());
                           if((f.getEndSpot() + pomjeraj + f.getBonusPositions()) <= paneList.size()){
                               f.setEndSpot(f.getEndSpot() + pomjeraj + f.getBonusPositions());}
                           else f.setEndSpot(paneList.size());
                           Platform.runLater(()->meaningOfCard.setText(this.toString()));
                           f.resetBonusPositions();

                       for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== false){
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x + 1)));
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(ff.getTriangle()));
                                   flag = true;
                               }else {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(ff.getTriangle()));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
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
                           Holes holes = new Holes(5,positionOnTheMap,mp,paneList,this);
                           holes.processHoles();
                   }}
                   else if("Super brza figura".equals(f.move())){

                       SuperSpeedFigure ssf = (SuperSpeedFigure) f;
                       if(pomjeraj!=5) {
                           positionOnTheMap.removeFromMap(this,f);
                           f.setStartSpot(f.getEndSpot());
                           if((f.getEndSpot() + (pomjeraj * 2) + f.getBonusPositions()) <= paneList.size()){
                               f.setEndSpot(f.getEndSpot() + (pomjeraj * 2) + f.getBonusPositions());}
                           else f.setEndSpot(paneList.size());
                           Platform.runLater(()->meaningOfCard.setText(this.toString()));
                           f.resetBonusPositions();

                       for (int i = f.getStartSpot(); i < f.getEndSpot(); i++) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== false){
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x + 1)));
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(ssf.getRectangle()));
                                   flag = true;
                               }else {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(ssf.getRectangle()));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
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
                       Holes holes = new Holes(5,positionOnTheMap,mp,paneList,this);
                       holes.processHoles();
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
    @Override
    public String toString() {
        return  "Na potezu je igrač " + this.name + "." + "\n" + "Pomijera se figura " +
                    numberOfFiguresThatAreDone + " ( " + figure.get(0).move() + " )." + "\n" + "Prelazi " + (figure.get(0).getEndSpot() - figure.get(0).getStartSpot()) + ", od pozicije " + numbers.get(figure.get(0).getStartSpot()) +
                    " do pozicije " + numbers.get(figure.get(0).getEndSpot() - 1) + ". \n" + "Uključujući bonus od " + figure.get(0).getBonusPositions() + " polja.";
    }

}
