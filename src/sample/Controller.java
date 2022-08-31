package sample;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.unibl.etf.pj2.projektni.model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    String bojaPrvogIgraca;
    String bojaDrugogIgraca;
    String bojaTrecegIgraca;
    String bojaCetvrtogIgraca;

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

    //Test
    List<Pane> tempsss = new ArrayList<>();
    private final PlayingDeck playingDeck;


    int brojac = 0;
   public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
       this.playingDeck = new PlayingDeck();
   }
   public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2, String ime3) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
       this.ime3 = ime3;
       this.playingDeck = new PlayingDeck();
   }
   public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2, String ime3, String ime4) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
       this.ime3 = ime3;
       this.ime4 = ime4;
       this.playingDeck = new PlayingDeck();

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

        napraviMatricu();
        System.out.println("BBBBBBB" + tempsss.size());
        for(int i = 0;i < tempsss.size();i++) {
            tempsss.get(i).setStyle("-fx-border-color: black; -fx-background-color:rgba(255, 255, 255, 0.87);");
        }
        podesavanjeImena();
        List<Pane> temp = new ArrayList<>();
        temp = player1.getPaneList();
        System.out.println("AAAAAAA" + temp.size());
        for(int i = 0; i < temp.size();i++) {
            temp.get(i).setStyle("-fx-border-color: black; -fx-background-color:rgba(0, 129, 255, 0.3)"); //lightred 255,99,71,0.5 light blude 0, 129, 255, 0.3
        }
        File file = new File("karte/6.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        createLabelForFigures();
         figures =   player1.getFigure();


    }
    public void napraviMatricu()
    {
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
   /*     for(int i = 0; i < dimenzijaMatrice; i++)
            for(int j = 0; j <dimenzijaMatrice; j++)
                panes[i][j].setStyle("-fx-border-color: black; ");
        gridPane.setAlignment(Pos.CENTER_RIGHT);*/

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
            List<Player> igraci = new ArrayList<Player>();
            igraci.add(player1);
            igraci.add(player2);
          //  igraci.add(player3);
          // igraci.add(player4);
            for(int i = 0; i < igraci.size(); i++) igraci.get(i).start();

   }
    public void podesavanjeImena() {
       if(brojIgraca == 2) {
            ime2Label.setText(ime1);
            ime3Label.setText(ime2);
            ime1Label.setVisible(false);
            ime4Label.setVisible(false);
            bojaPrvogIgraca = "zuta";
            bojaDrugogIgraca = "zelena";
            player1 = new Player(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);
            player2 = new Player(ime2, bojaDrugogIgraca, panes,dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);
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
           player1 = new Player(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);
           player2 = new Player(ime2, bojaDrugogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);
           player3 = new Player(ime3, bojaTrecegIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);
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
           player1 = new Player(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);
           player2 = new Player(ime2, bojaDrugogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);
           player3 = new Player(ime3, bojaTrecegIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);
           player4 = new Player(ime4, bojaCetvrtogIgraca, panes, dimenzijaMatrice,playingDeck,imageView,positionOnTheMap);

       }
    }
    public void createLabelForFigures() {
        List<Figure> figureList1;
        List<Figure> figureList2;
        List<Figure> figureList3;
        List<Figure> figureList4;
        List<Label> labels = new ArrayList<Label>();
            if(brojIgraca == 2) {
                figureList1 = player1.getFigure();
                figureList2 = player2.getFigure();

                for(int i = 0; i < 4; i++) {
                    Label l = new Label(player1.getNames()+ ": " + figureList1.get(i).move());
                    l.setTextFill(Color.YELLOW);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < 4; i++) {
                    Label l = new Label(player2.getNames()+ ": " + figureList2.get(i).move());
                    l.setTextFill(Color.GREEN);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < labels.size(); i++) {
                    Label tmp = labels.get(i);
                    tmp.setLayoutX(5);
                    tmp.setLayoutY(i*20);
                    pane3.getChildren().add(tmp);
                }

            }
            else if(brojIgraca == 3) {
                figureList1 = player1.getFigure();
                figureList2 = player2.getFigure();
                figureList3 = player3.getFigure();
                for(int i = 0; i < 4; i++) {
                    Label l = new Label(player1.getNames()+ ": " + figureList1.get(i).move());
                    l.setTextFill(Color.RED);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < 4; i++) {
                    Label l = new Label(player2.getNames()+ ": " + figureList2.get(i).move());
                    l.setTextFill(Color.YELLOW);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < 4; i++) {
                    Label l = new Label(player3.getNames() + ": " + figureList3.get(i).move());
                    l.setTextFill(Color.GREEN);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < labels.size(); i++) {
                    Label tmp = labels.get(i);
                    tmp.setLayoutX(5);
                    tmp.setLayoutY(i*20);
                    pane3.getChildren().add(tmp);
                }
            }
            else {
                figureList1 = player1.getFigure();
                figureList2 = player2.getFigure();
                figureList3 = player3.getFigure();
                figureList4 = player4.getFigure();
                for(int i = 0; i < 4; i++) {
                    Label l = new Label( player1.getNames() + ": " + figureList1.get(i).move());
                    l.setTextFill(Color.RED);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < 4; i++) {
                    Label l = new Label(player2.getNames() + ": " + figureList2.get(i).move());
                    l.setTextFill(Color.YELLOW);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < 4; i++) {
                    Label l = new Label(player3.getNames() + ": " + figureList3.get(i).move());
                    l.setTextFill(Color.GREEN);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < 4; i++) {
                    Label l = new Label(player4.getNames() + ": " + figureList4.get(i).move());
                    l.setTextFill(Color.BLUE);
                    l.setOnMouseClicked(mouseEvent -> {
                        System.out.println("Mahrina je car");
                    });
                    labels.add(l);
                }
                for(int i = 0; i < labels.size(); i++) {
                    Label tmp = labels.get(i);
                    tmp.setLayoutX(5);
                    tmp.setLayoutY(i*20);
                    pane3.getChildren().add(tmp);
                }


            }

    }

}
