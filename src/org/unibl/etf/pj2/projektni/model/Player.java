package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.Controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Thread{

    static public final AtomicInteger indexToPrint = new AtomicInteger(0);
    static private int threadNumber = 0;
    final private int index;
    static int numberOfFiguresThatEnd = 0;
    static int numberOfPlayersThatAreDone;
    public static boolean pause = false;

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
    Holes holes;
    ResultProcessing resultProcessing;
    MyTimer myTimer;


    public Player(String name, String colour, Pane[][] panes, int matrixDimension,PlayingDeck playingDeck,ImageView imageView, PositionOnTheMap positionOnTheMap, int positionOfPlayer, GhostFigure ghostFigure, ArrayList<Label> labels, Label meaningOfCard, Label timeLabel, Holes holes,ResultProcessing rs,MyTimer timer) {
        this.myTimer = timer;
        this.resultProcessing = rs;
        resultProcessing.addPlayerToList(this);
        this.holes = holes;
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
        this.mp = new MovingPath(orginalPane, matrixDimension, labels);
        if(matrixDimension % 2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();

        paneList = mp.getPaneList();
        this.numbers = mp.getNumbers();
        this.ghostFigure = ghostFigure;
        PlayingDeckForGet pdfg = new PlayingDeckForGet(playingDeck);
        Producer producer = new Producer(pdfg);
        consumer = new Consumer(pdfg,imageView);
        dodajFigure();
        producer.start();
        this.holes = holes;
        this.holes.addPlayer(this);
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
                SimpleFigure of = new SimpleFigure(colour, orginalPane, matrixDimension, mp, positionOnTheMap, ghostFigure);
                figure.add(of);
            }
            else if(br == 1) {
                FlyingFigure lf = new FlyingFigure(colour, orginalPane, matrixDimension, mp, positionOnTheMap, ghostFigure);
                figure.add(lf);
            }
            else {
                SuperSpeedFigure sbf = new SuperSpeedFigure(colour, orginalPane, matrixDimension, mp, positionOnTheMap, ghostFigure);
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
            ghostFigure.isDaemon();
            ghostFigure.start();
            isGhostStarted = true;

            myTimer.start();
        }
        while(true) {
            synchronized (indexToPrint) {
                while(indexToPrint.get()!=index){
                    try {
                        indexToPrint.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Figure f = figure.get(0);
                if (f.getIsDone()) {
                    Figure temp = figure.remove(0);
                    figure.add(temp);
                    f = figure.get(0);
                    numberOfFiguresThatAreDone++;
                }
                if(numberOfPlayersThatAreDone == Controller.getNumberOfPlayers()) {
                    System.out.println("SVI IGRACI SU ZAVRSILI");
                    resultProcessing.setTimeOfPlay(myTimer.getSecond());
                    resultProcessing.processing();
                }
                if(numberOfFiguresThatAreDone <= 4){
                    PlayingCard pc = consumer.getCard();
                    int pomjeraj = pc.getNumber();
                    if(pomjeraj!=5) {
                        positionOnTheMap.removeFromMap(this,f);
                        f.setStartSpot(f.getEndSpot());
                        if("Super brza figura".equals(f.move())) {
                            if((f.getEndSpot() + (pomjeraj * 2) + f.getBonusPositions()) <= paneList.size()) {
                                f.setEndSpot(f.getEndSpot() + (pomjeraj * 2) + f.getBonusPositions());
                            }
                            else {
                                f.setEndSpot(paneList.size());
                            }
                        } else {
                            if((f.getEndSpot() + pomjeraj + f.getBonusPositions()) <= paneList.size()){
                                f.setEndSpot(f.getEndSpot() + pomjeraj + f.getBonusPositions());
                            } else {
                                f.setEndSpot(paneList.size());
                            }
                        }
                        printOnScreen(f.getBonusPositions());
                        f.resetBonusPositions();
                        f.drawFigure();
                        if(f.getEndSpot()<paneList.size())
                            positionOnTheMap.addOnMap(this,paneList.get(f.getEndSpot() - 1),f);
                    } else {
                        holes.setPositionOnTheMap(positionOnTheMap);
                        holes.processHoles();
                    }
                    indexToPrint.set(nextIndex());
                    indexToPrint.notifyAll();
                } else {
                    if(numberOfPlayersThatAreDone!=Controller.getNumberOfPlayers()) {
                        numberOfPlayersThatAreDone++;
                    }
                    indexToPrint.set(nextIndex());
                    indexToPrint.notifyAll();
                }
            }
        }

    }
/*

   @Override
    public synchronized void run() {

            if(positionOfPlayer == 1 && isGhostStarted == false) {
                ghostFigure.isDaemon();
                ghostFigure.start();
                isGhostStarted = true;
              //  myTimer = new MyTimer(this.timeLabel);
                myTimer.start();
            }
           while(true){

               synchronized (indexToPrint) {
                   while(indexToPrint.get()!=index){
                       try {
                           indexToPrint.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
                   if(pause == false) {
                   Figure f = figure.get(0);
                   if (f.getIsDone()) {
                       Figure temp = figure.remove(0);
                       figure.add(temp);
                       f = figure.get(0);
                       numberOfFiguresThatAreDone++;
                   }
                   if(numberOfPlayersThatAreDone == Controller.getNumberOfPlayers()) {
                       System.out.println("SVI IGRACI SU ZAVRSILI");
                       resultProcessing.setTimeOfPlay(myTimer.getSecond());
                        resultProcessing.processing();
                   }




                   if(numberOfFiguresThatAreDone <= 4){
                       //System.out.println("BROJ FIGURA KOJE SU ZAVRSILE " + numberOfFiguresThatAreDone);


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
                            if(pause == false) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x)) == false){
                                   f.addProcessedPositions();
                                   f.addPosition(paneList.get(x+1));
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x + 1)));
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(sf.getCircle()));
                                   flag = true;

                               }else {
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(sf.getCircle()));
                                   f.addProcessedPositions();
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                                   f.addPosition(paneList.get(x));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                                   f.addProcessedPositions();
                                   f.addPosition(paneList.get(x));
                                   Platform.runLater(()->paneList.get(x).getChildren().add(sf.getCircle()));
                               }
                           }
                           try{
                               sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }else {
                                try {
                                    indexToPrint.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
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
                           holes.setPositionOnTheMap(positionOnTheMap);
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
                           if (pause == false) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== false){
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x + 1)));
                                   f.addProcessedPositions();
                                   f.addPosition(paneList.get(x+1));
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(ff.getTriangle()));
                                   flag = true;
                               }else {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(ff.getTriangle()));
                                   f.addProcessedPositions();
                                   f.addPosition(paneList.get(x));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                                   Platform.runLater(()->paneList.get(x).getChildren().add(ff.getTriangle()));
                                   f.addProcessedPositions();
                                   f.addPosition(paneList.get(x));

                               }
                           }
                           try{
                               sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       } else {
                               try {
                                   indexToPrint.wait();
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }
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
                           holes.setPositionOnTheMap(positionOnTheMap);

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
                           if (pause == false) {
                           final int x = i;
                           if(x == f.getEndSpot() - 1) {
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== false){
                                   f.addProcessedPositions();
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x + 1)));
                                   Platform.runLater(()->paneList.get(x + 1).getChildren().add(ssf.getRectangle()));
                                   f.addPosition(paneList.get(x+1));
                                   flag = true;
                               }else {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                                   f.addProcessedPositions();
                                   f.addPosition(paneList.get(x));
                                   Platform.runLater(() -> paneList.get(x).getChildren().add(ssf.getRectangle()));
                               }
                           }else{
                               if(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(x))== true) {
                                   f.setBonusPositions(ghostFigure.checkForBonus(paneList.get(x)));
                                   f.addProcessedPositions();
                                   f.addPosition(paneList.get(x));

                                   Platform.runLater(()->paneList.get(x).getChildren().add(ssf.getRectangle()));
                               }
                           }
                           try{
                               sleep(1000);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }else {
                               try {
                                   indexToPrint.wait();
                               } catch (InterruptedException e) {
                                   e.printStackTrace();
                               }
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
                           holes.setPositionOnTheMap(positionOnTheMap);

                       holes.processHoles();
                   }}
                   indexToPrint.set(nextIndex());
                   indexToPrint.notifyAll();
               }
                   else{
                 //      System.out.println("SVE FIGURE ZA IGACA " + this.name + " ZAVRSILE");
                     //  int p = new File(System.getProperty("user.dir") + File.separator + "rezultati").list().length;
                     //  System.out.println("broj fajlova " + p);
                       if(numberOfPlayersThatAreDone!=Controller.getNumberOfPlayers()) {
                           numberOfPlayersThatAreDone++;
                       }
                       indexToPrint.set(nextIndex());
                       indexToPrint.notifyAll();
                   }
           }else {
                  //    indexToPrint.set(nextIndex());
                       indexToPrint.notifyAll();
                   }
               }
       }
   }*/
    public List<Pane> getPaneList() {
        return this.paneList;
    }
    @Override
    public String toString() {
        return  "Na potezu je igrač " + this.name + "." + "\n" + "Pomijera se figura " +
                    numberOfFiguresThatAreDone + " ( " + figure.get(0).move() + " )." + "\n" + "Prelazi " + (figure.get(0).getEndSpot() - figure.get(0).getStartSpot()) + ", od pozicije " + numbers.get(figure.get(0).getStartSpot()) +
                    " do pozicije " + numbers.get(figure.get(0).getEndSpot() - 1) + ". \n" + "Uključujući bonus od ";
    }
    public void printOnScreen(int bonus) {
        String text = this.toString() + bonus + " polja.";
        meaningOfCard.setMaxWidth(Double.MAX_VALUE);
        meaningOfCard.setAlignment(Pos.CENTER);
        Platform.runLater(()->meaningOfCard.setText(text));
    }

}
