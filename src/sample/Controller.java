package sample;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.unibl.etf.pj2.projektni.exception.LoggingException;
import org.unibl.etf.pj2.projektni.model.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class Controller implements Initializable {
    @FXML
    Button pokreni = new Button();
   @FXML
   Label label = new Label();
   @FXML
   Label label2 = new Label();
   @FXML
   GridPane gridPane = new GridPane();
   @FXML
    Pane [][] panes;
   @FXML
    Pane pane1 = new Pane();
   @FXML
    Pane pane2 = new Pane();
   @FXML
    Pane pane3 = new Pane();
    @FXML
    Pane pane4 = new Pane();
    @FXML
    Pane pane5 = new Pane();
    @FXML
    Pane pane6 = new Pane();
    @FXML
    Pane pane7 = new Pane();
    @FXML
    Label ime1Label = new Label();
    @FXML
    Label ime2Label = new Label();
    @FXML
    Label ime3Label = new Label();
    @FXML
    Label ime4Label = new Label();
    @FXML
    ImageView imageView;
    @FXML
    Label meaningOfCard = new Label();
    @FXML
    Button files;

    String bojaPrvogIgraca;
    String bojaDrugogIgraca;
    String bojaTrecegIgraca;
    String bojaCetvrtogIgraca;

    boolean firstRun = false;
    boolean pause = false;


    Player player1;
    Player player2;
    Player player3;
    Player player4;  //C:\Users\Boris\OneDrive\Desktop\JAVA-PROJEKTNI-MAJ2022\karte

    List<PlayingCard> pastCards = new ArrayList<>();

    Boolean on = false;
    @FXML
    Label timerLabel;
    int dimenzijaMatrice;
    int brojIgraca;
    String ime1;
    String ime2;
    String ime3;
    String ime4;
    ArrayList<Label> labele = new ArrayList<>();
    ArrayList<Figure> sveFigure = new ArrayList<>();
    PlayingDeck pd;
    List<Figure> figures;
    PositionOnTheMap positionOnTheMap = new PositionOnTheMap();
    static int numberOfPlayers;
    //Test
    List<Pane> tempsss = new ArrayList<>();
    private final PlayingDeck playingDeck;
    List<Player> listOfPlayers = new ArrayList<>();
    MovingPath mp;
    GhostFigure ghost;
    Holes holes;
    ResultProcessing resultProcessing;
    MyTimer myTimer;
    List<String> listOfNames = new ArrayList<>();
    PlayingDeckForGet pdfg;
    Producer producer;
    Consumer consumer;

    int brojac = 0;
    public Controller(List<String> listOfNames, int numberOfPlayer, int matrixDimension) {
        this.listOfNames = listOfNames;
        if(numberOfPlayer == 2) {
            this.ime1 = listOfNames.remove(0);
            this.ime2 = listOfNames.remove(0);
        }else if(numberOfPlayer == 3) {
            this.ime1 = listOfNames.remove(0);
            this.ime2 = listOfNames.remove(0);
            this.ime3 = listOfNames.remove(0);
        }else
        {
            this.ime1 = listOfNames.remove(0);
            this.ime2 = listOfNames.remove(0);
            this.ime3 = listOfNames.remove(0);
            this.ime4 = listOfNames.remove(0);
        }
        this.dimenzijaMatrice = matrixDimension;
        this.playingDeck = new PlayingDeck();
        numberOfPlayers = numberOfPlayer;

    }
    public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
       this.playingDeck = new PlayingDeck();
       numberOfPlayers = brojIgraca;

   }
    public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2, String ime3) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
       this.ime3 = ime3;
       this.playingDeck = new PlayingDeck();
       numberOfPlayers = brojIgraca;
   }
    public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2, String ime3, String ime4) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
       this.ime3 = ime3;
       this.ime4 = ime4;
       this.playingDeck = new PlayingDeck();
       numberOfPlayers = brojIgraca;
   }
    public static int getNumberOfPlayers() {
       return  numberOfPlayers;
   }
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        pane1.setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");
        pane2.setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");
        pane3.setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");
        pane4.setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");
        pane5.setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");
        pane6.setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");
        pane7.setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");

        label.setText("Trenutni broj odigranih igara: 10");
        label2.setText("DIAMOND CIRCLE");
        label2.setAlignment(Pos.CENTER);
        timerLabel.setText("");
        myTimer = new MyTimer(timerLabel);
        napraviMatricu();
        mp = new MovingPath(panes,dimenzijaMatrice,labele);
        if(dimenzijaMatrice % 2 == 0) mp.addToListEvenNumber();
        else mp.addToListOddNumber();
        int number = 0;
        try {
            number = readFileWithNumberOfHoles();
        } catch (IOException e) {
            LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }
        resultProcessing = new ResultProcessing(mp);
        holes = new Holes(number,mp,mp.getPaneList());
        for(int i = 0;i < tempsss.size();i++) {
            tempsss.get(i).setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");
        }
        pdfg = new PlayingDeckForGet(playingDeck);
        producer = new Producer(pdfg);
        consumer = new Consumer(pdfg,imageView);
        podesavanjeImena();
        List<Pane> temp = new ArrayList<>();
        temp = player1.getPaneList();
        for(int i = 0; i < temp.size();i++) {
            temp.get(i).setStyle("-fx-border-color: black; -fx-background-color:rgba(0, 129, 255, 0.3)"); //lightred 255,99,71,0.5 light blude 0, 129, 255, 0.3
        }
        File file = new File("karte/6.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        createPathForFigure();
        meaningOfCard.setWrapText(true);
        int p = new File(System.getProperty("user.dir") + File.separator + "rezultati").list().length;
        label.setText("Trenutni broj odigranih igara: " + p);

    }
    public void napraviMatricu() {
        panes = new Pane[dimenzijaMatrice][dimenzijaMatrice];
        for(int i = 1; i <= dimenzijaMatrice * dimenzijaMatrice; i++) {
            Label temp = new Label();
            temp.setText(String.valueOf(i));
            labele.add(temp);
        }
        for(int i = 0;i < dimenzijaMatrice; i++)
            for(int j = 0;j < dimenzijaMatrice; j++) {
                panes[i][j] = new Pane();
                gridPane.add(panes[i][j],i,j);
                tempsss.add(panes[i][j]);
                if(dimenzijaMatrice == 7) {
                panes[i][j].setMinWidth(22);
                panes[i][j].setMinHeight(22);
                } else if(dimenzijaMatrice == 8) {
                    panes[i][j].setMinWidth(20);
                    panes[i][j].setMinHeight(20);
                }

                panes[i][j].setStyle("-fx-border-color: black;"); //-fx-background-color:rgba(255, 255, 255, 0.87);
            }

        for(int i = 0; i < dimenzijaMatrice; i++)
            for(int j = 0; j < dimenzijaMatrice; j++)
            {
                Label tmp = labele.remove(0);
                tmp.setMaxWidth(Double.MAX_VALUE);
                tmp.setAlignment(Pos.CENTER);
                gridPane.add(tmp,j,i);
            }
        if(dimenzijaMatrice == 7) {
            gridPane.setLayoutX(220);
            gridPane.setLayoutY(150);
            gridPane.setPrefSize(420, 420);
        }else if(dimenzijaMatrice == 8) {
            gridPane.setLayoutX(200);
            gridPane.setLayoutY(130);
            gridPane.setPrefSize(420, 420);
        } else if(dimenzijaMatrice == 9) {
            gridPane.setLayoutX(200);
            gridPane.setLayoutY(130);
            gridPane.setPrefSize(370, 370);
        } else {
            gridPane.setLayoutX(200);
            gridPane.setLayoutY(130);
            gridPane.setPrefSize(340, 340);
        }
    }
    @FXML
    public void zapocni(javafx.event.ActionEvent ae) throws InterruptedException {
         //   List<Player> igraci = new ArrayList<Player>();
            if(firstRun == false) {
             //   igraci.add(player1);
             //   igraci.add(player2);
                for (int i = 0; i < listOfPlayers.size(); i++) listOfPlayers.get(i).start();
                firstRun = true;
                pokreni.setText("Zaustavi");

            }else {
            if(pause == false && firstRun == true) {
                Figure.pause = true;
                pause = true;
                Player.pause = true;
                myTimer.pause = true;
                pokreni.setText("Pokreni");
            }else {
                myTimer.pause = false;
                synchronized (Player.indexToPrint) {
                    Player.indexToPrint.notifyAll();
                    Figure.pause = false;
                    pause = false;
                    Player.pause = false;

                }
                pokreni.setText("Zaustavi");

            } }


   }
    public void podesavanjeImena() {
        GhostFigure ghostFigure;
        ghostFigure = new GhostFigure(mp.getPaneList(),dimenzijaMatrice);
       if(brojIgraca == 2) {
            ime2Label.setText(ime1);
            ime3Label.setText(ime2);
            ime1Label.setVisible(false);
            ime4Label.setVisible(false);
            bojaPrvogIgraca = "zuta";
            bojaDrugogIgraca = "zelena";
            player1 = new Player(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,1,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
            player2 = new Player(ime2, bojaDrugogIgraca, panes,dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,2,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
            listOfPlayers.add(player1);
            listOfPlayers.add(player2);
       }
       else if(brojIgraca == 3) {
           ime1Label.setText(ime1);
           ime2Label.setText(ime2);
           ime3Label.setText(ime3);
           ime4Label.setVisible(false);
           ime1Label.setAlignment(Pos.CENTER_LEFT);
           ime2Label.setAlignment(Pos.CENTER);
           ime3Label.setAlignment(Pos.CENTER_RIGHT);
           bojaPrvogIgraca = "crvena";
           bojaDrugogIgraca = "zuta";
           bojaTrecegIgraca = "zelena";
           player1 = new Player(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,1,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
           player2 = new Player(ime2, bojaDrugogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,2,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
           player3 = new Player(ime3, bojaTrecegIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,3,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
           listOfPlayers.add(player1);
           listOfPlayers.add(player2);
           listOfPlayers.add(player3);
       }
       else {
           ime1Label.setText(ime1);
           ime2Label.setText(ime2);
           ime3Label.setText(ime3);
           ime4Label.setText(ime4);
           bojaPrvogIgraca = "crvena";
           bojaDrugogIgraca = "zuta";
           bojaTrecegIgraca = "zelena";
           bojaCetvrtogIgraca = "plava";
           player1 = new Player(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,1,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
           player2 = new Player(ime2, bojaDrugogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,2,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
           player3 = new Player(ime3, bojaTrecegIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,3,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
           player4 = new Player(ime4, bojaCetvrtogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap,4,ghostFigure, labele, meaningOfCard, timerLabel, holes,resultProcessing,myTimer,pdfg,producer,consumer);
           listOfPlayers.add(player1);
           listOfPlayers.add(player2);
           listOfPlayers.add(player3);
           listOfPlayers.add(player4);

       }
    }
    public void createPathForFigure() {
        List<Figure> figureList = new ArrayList<>();
        for(int i = 0; i < listOfPlayers.size(); i++) {
            figureList.addAll(listOfPlayers.get(i).getFigure());
        }
        int temp = 0;
        for(int i = 0; i < listOfPlayers.size(); i++) {
            for(int j = 0; j < 4; j++) {
                Figure f = figureList.remove(0);
                Label label = new Label();
                final int pl = i;
                label.setText(listOfPlayers.get(i).getNames()+ ": " + f.move());
                paintLabel(listOfPlayers.get(i), label);
                label.setLayoutX(5);
                label.setLayoutY((temp++)*20);
                label.setFont(Font.font("System", FontWeight.BOLD,12));
                pane3.getChildren().add(label);
                label.setOnMouseClicked((MouseEvent event)->{
                    try {
                        doOnClick(listOfPlayers.get(pl),f);
                    } catch (IOException e) {
                        LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                    }
                });
       /*         label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            doOnClick(listOfPlayers.get(pl),f);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });*/

            }
        }
    }
    public void paintLabel(Player player, Label l) {
        if("crvena".equals(player.getColour())) {
            l.setTextFill(Color.RED);
        }else if("plava".equals(player.getColour())){
            l.setTextFill(Color.BLUE);
        }else if("zelena".equals(player.getColour())) {
            l.setTextFill(Color.GREEN);
        }else {
            l.setTextFill(Color.YELLOW);
        }
    }
    public void doOnClick(Player player, Figure figure) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CrossedPath.fxml"));
        loader.setController(new CrossedPathController(player,figure,dimenzijaMatrice));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Pređen put");
        primaryStage.setScene(new Scene(root, 368, 400));
        primaryStage.show();
    }
    @FXML
    public void listOfFiles(javafx.event.ActionEvent ae) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ListOfFiles.fxml"));
        Parent root = loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Fajlovi");
        primaryStage.setScene(new Scene(root, 368, 400));
        primaryStage.show();
    }
    public int readFileWithNumberOfHoles() throws IOException {
        String path = System.getProperty("user.dir") + File.separator + "brojRupa.txt";
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String str = br.readLine();
        int number = Integer.parseInt(str);
        return number;
    }


}
