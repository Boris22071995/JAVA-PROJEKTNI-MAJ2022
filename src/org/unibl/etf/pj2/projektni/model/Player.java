package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.unibl.etf.pj2.projektni.exception.LoggingException;
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
import java.util.logging.Level;

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
    List<Figure> listOfFiguresForResults = new ArrayList<>();
    PlayingDeckForGet pdfg;
    Producer producer;




    public Player(String name, String colour, Pane[][] panes, int matrixDimension,PlayingDeck playingDeck,ImageView imageView, PositionOnTheMap positionOnTheMap, int positionOfPlayer, GhostFigure ghostFigure, ArrayList<Label> labels, Label meaningOfCard, Label timeLabel, Holes holes,ResultProcessing rs,MyTimer timer,PlayingDeckForGet pdfg, Producer producer, Consumer consumer) {
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
         this.pdfg = pdfg;
        this.producer = producer;
        this.consumer = consumer;
        dodajFigure();
        this.holes = holes;
        this.holes.addPlayer(this);
        listOfFiguresForResults.addAll(figure);
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
    public List<Figure> getListOfFiguresForResults() {
        return this.listOfFiguresForResults;
    }
    @Override
    public synchronized void run() {
        if(positionOfPlayer == 1 && isGhostStarted == false) {
            ghostFigure.isDaemon();
            ghostFigure.start();
            isGhostStarted = true;
            producer.start();
            myTimer.start();
        }
        while(true) {
            synchronized (indexToPrint) {
                while(indexToPrint.get()!=index){
                    try {
                        indexToPrint.wait();
                    } catch (InterruptedException e) {
                        LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
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
                    if(pause == true) {
                        try {
                            indexToPrint.wait();
                        } catch (InterruptedException e) {
                            LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                        }
                    }
                    PlayingCard pc = consumer.getCard();
                    int pomjeraj = pc.getNumber();
                    if(pomjeraj!=5) {
                        positionOnTheMap.removeFromMap(this,f);
                        f.setStartSpot(f.getEndSpot());
                        if("Super brza figura".equals(f.move())) {
                            if((f.getEndSpot() + (pomjeraj * 2) + f.getBonusPositions()) <= paneList.size()) {
                                f.setEndSpot(f.getEndSpot() + (pomjeraj * 2) + f.getBonusPositions());
                                f.setEndSpot(checkForEndSpot(f.getEndSpot()));
                            }
                            else {
                                f.setEndSpot(paneList.size());
                            }
                        } else {
                            if((f.getEndSpot() + pomjeraj + f.getBonusPositions()) <= paneList.size()){
                                f.setEndSpot(f.getEndSpot() + pomjeraj + f.getBonusPositions());
                                f.setEndSpot(checkForEndSpot(f.getEndSpot()));
                            } else {
                                f.setEndSpot(paneList.size());
                            }
                        }
                        printOnScreen(f.getBonusPositions());
                        f.resetBonusPositions();
                        try {
                            f.drawFigure();
                        } catch (InterruptedException e) {
                            LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                        }
                        if(f.getEndSpot()<paneList.size())
                            positionOnTheMap.addOnMap(this,paneList.get(f.getEndSpot() - 1),f);
                    } else {
                        if(pause == true) {
                            try {
                                indexToPrint.wait();
                            } catch (InterruptedException e) {
                                LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                            }
                        }
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
    public int checkForEndSpot(int position) {
        int temp = position - 1;
        while(positionOnTheMap.checkForAvalibalitiOfPosition(paneList.get(temp)) == false && temp < paneList.size()) {
            temp++;
        }
        return temp + 1;
    }
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
    public int getPositionOfPlayer() {
        return this.positionOfPlayer;
    }

}
