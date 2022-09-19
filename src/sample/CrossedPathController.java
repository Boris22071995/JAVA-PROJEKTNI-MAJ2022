package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.unibl.etf.pj2.projektni.model.Figure;
import org.unibl.etf.pj2.projektni.model.MovingPath;
import org.unibl.etf.pj2.projektni.model.Player;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CrossedPathController implements Initializable {
    @FXML
    Label name;
    @FXML
    Label figureName;
    @FXML
    GridPane gridPane;
    @FXML
    Pane[][] panes;
    @FXML
    Label timeInGame;
    ArrayList<Label> labele = new ArrayList<>();
    List<Pane> tempsss = new ArrayList<>();
    MovingPath mp;
    List<Pane> paneList;

    int matrixDimension;
    Player player;
    Figure figure;
    public CrossedPathController() {

    }
    public CrossedPathController(Player player, Figure figure, int matrixDimension) {
        this.player = player;
        this.figure = figure;
        this.matrixDimension = matrixDimension;

    }
    @Override
    public  void initialize(URL url, ResourceBundle resources){
            name.setText(this.player.getNames());
            figureName.setText(this.figure.move());
            timeInGame.setText("Vrijeme u igri: " + String.valueOf(figure.timeInGame()) + " s");
            napraviMatricu();
        mp = new MovingPath(panes,matrixDimension,labele);
        if(matrixDimension%2 == 0)
            mp.addToListEvenNumber();
        else
            mp.addToListOddNumber();
        paneList = mp.getPaneList();
        if(figure.getNumberOfProcessedPositions()!=0) {
            for (int i = 0; i < figure.getProcessedPath().size(); i++) {
                paneList.get(i).setStyle("-fx-border-color: black; -fx-background-color:rgba(0, 129, 255, 0.3)");
            }
        }
    }
    public void napraviMatricu() {
        panes = new Pane[matrixDimension][matrixDimension];
        for(int i = 1; i <= matrixDimension * matrixDimension; i++) {
            Label temp = new Label();
            temp.setText(String.valueOf(i));
            labele.add(temp);
        }
        for(int i = 0;i < matrixDimension; i++)
            for(int j = 0;j < matrixDimension; j++) {
                panes[i][j] = new Pane();
                gridPane.add(panes[i][j],i,j);
                tempsss.add(panes[i][j]);
                if(matrixDimension == 7) {
                    panes[i][j].setMinWidth(22);
                    panes[i][j].setMinHeight(22);
                } else if(matrixDimension == 8) {
                    panes[i][j].setMinWidth(20);
                    panes[i][j].setMinHeight(20);
                }

                panes[i][j].setStyle("-fx-border-color: black;"); //-fx-background-color:rgba(255, 255, 255, 0.87);
            }

        for(int i = 0; i < matrixDimension; i++)
            for(int j = 0; j < matrixDimension; j++)
            {
                Label tmp = labele.remove(0);
                tmp.setMaxWidth(Double.MAX_VALUE);
                tmp.setAlignment(Pos.CENTER);
                gridPane.add(tmp,j,i);
            }
        if(matrixDimension == 7) {
            gridPane.setLayoutX(45);
            gridPane.setLayoutY(100);
            gridPane.setPrefSize(400, 400);
        }else if(matrixDimension == 8) {
            gridPane.setLayoutX(45);
            gridPane.setLayoutY(100);
            gridPane.setPrefSize(360, 360);
        } else if(matrixDimension == 9) {
            gridPane.setLayoutX(45);
            gridPane.setLayoutY(100);
            gridPane.setPrefSize(320, 320);
        } else {
            gridPane.setLayoutX(45);
            gridPane.setLayoutY(100);
            gridPane.setPrefSize(280, 280);
        }
    }


}
