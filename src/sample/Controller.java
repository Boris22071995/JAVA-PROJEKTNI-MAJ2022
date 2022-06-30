package sample;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.unibl.etf.pj2.projektni.model.Igrac;
import org.unibl.etf.pj2.projektni.model.LebdecaFigura;
import org.unibl.etf.pj2.projektni.model.ObicnaFigura;
import org.unibl.etf.pj2.projektni.model.SuperBrzaFigura;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    String bojaPrvogIgraca;
    String bojaDrugogIgraca;
    String bojaTrecegIgraca;
    String bojaCetvrtogIgraca;

    Igrac igrac1;
    Igrac igrac2;
    Igrac igrac3;
    Igrac igrac4;

    int dimenzijaMatrice;
    int brojIgraca;
    String ime1;
    String ime2;
    String ime3;
    String ime4;
   ArrayList<Label> labele = new ArrayList<Label>();
   public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
   }
   public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2, String ime3) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
       this.ime3 = ime3;
   }
   public Controller(int dimenzijaMatrice, int brojIgraca, String ime1, String ime2, String ime3, String ime4) throws IOException {
       this.dimenzijaMatrice = dimenzijaMatrice;
       this.brojIgraca = brojIgraca;
       this.ime1 = ime1;
       this.ime2 = ime2;
       this.ime3 = ime3;
       this.ime4 = ime4;
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
        podesavanjeImena();
        napraviMatricu();

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
                if(dimenzijaMatrice == 7) {
                panes[i][j].setMinWidth(22);
                panes[i][j].setMinHeight(22);
                } else if(dimenzijaMatrice == 8) {
                    panes[i][j].setMinWidth(20);
                    panes[i][j].setMinHeight(20);
                }

                panes[i][j].setStyle("-fx-border-color: black; "); //-fx-background-color:rgba(255, 255, 255, 0.87);
            }
        for(int i = 0; i < dimenzijaMatrice; i++)
            for(int j = 0; j <dimenzijaMatrice; j++)
                panes[i][j].setStyle("-fx-border-color: black; ");
        gridPane.setAlignment(Pos.CENTER_RIGHT);

        for(int i = 0; i < dimenzijaMatrice; i++)
            for(int j = 0; j < dimenzijaMatrice; j++)
            {
                Label tmp = labele.remove(0);
                tmp.setAlignment(Pos.CENTER);
                gridPane.add(tmp,j,i);
            }
        if(dimenzijaMatrice == 7) {
            gridPane.setLayoutX(160);
            gridPane.setLayoutY(150);
            gridPane.setPrefSize(420, 420);
        }else if(dimenzijaMatrice == 8) {
            gridPane.setLayoutX(140);
            gridPane.setLayoutY(130);
            gridPane.setPrefSize(420, 420);
        } else if(dimenzijaMatrice == 9) {
            gridPane.setLayoutX(140);
            gridPane.setLayoutY(130);
            gridPane.setPrefSize(370, 370);
        } else {
            gridPane.setLayoutX(140);
            gridPane.setLayoutY(130);
            gridPane.setPrefSize(340, 340);
        }
    }

    @FXML
    public void zapocni(javafx.event.ActionEvent ae) throws InterruptedException {
        LebdecaFigura lf = new LebdecaFigura("crvena", panes, dimenzijaMatrice);
     //   lf.start();
    //    for(int i = 0; i < 1000;i++);
        ObicnaFigura of = new ObicnaFigura("plava",panes,dimenzijaMatrice);
        of.start();
        SuperBrzaFigura sbf = new SuperBrzaFigura("zelena",panes,dimenzijaMatrice);
   //     sbf.start();


    }

    public void podesavanjeImena() {
       if(brojIgraca == 2) {
            ime2Label.setText(ime1);
            ime3Label.setText(ime2);
            ime1Label.setVisible(false);
            ime4Label.setVisible(false);
            bojaPrvogIgraca = "zuta";
            bojaDrugogIgraca = "zelena";
            igrac1 = new Igrac(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice);
            igrac2 = new Igrac(ime2, bojaDrugogIgraca, panes,dimenzijaMatrice);
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
           igrac1 = new Igrac(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice);
           igrac2 = new Igrac(ime2, bojaDrugogIgraca, panes, dimenzijaMatrice);
           igrac3 = new Igrac(ime3, bojaTrecegIgraca, panes, dimenzijaMatrice);
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
           igrac1 = new Igrac(ime1, bojaPrvogIgraca, panes, dimenzijaMatrice);
           igrac2 = new Igrac(ime2, bojaDrugogIgraca, panes, dimenzijaMatrice);
           igrac3 = new Igrac(ime3, bojaTrecegIgraca, panes, dimenzijaMatrice);
           igrac4 = new Igrac(ime4, bojaCetvrtogIgraca, panes, dimenzijaMatrice);

       }
    }

}
