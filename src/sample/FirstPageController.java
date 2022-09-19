package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.unibl.etf.pj2.projektni.exception.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class FirstPageController {

    @FXML
    Button zapocni = new Button();
    @FXML
    TextField dimenzijaX = new TextField();
    @FXML
    TextField brojIgraca = new TextField();
    @FXML
    TextField imePrvogIgraca = new TextField();
    @FXML
    TextField imeDrugogIgraca = new TextField();
    @FXML
    TextField imeTrecegIgraca = new TextField();
    @FXML
    TextField imeCetvrtogIgraca = new TextField();

    int xDimenzija;
    int brIgraca;
    String name1;
    String name2;
    String name3;
    String name4;
    List<String> listOfNames = new ArrayList<>();
    boolean validInput = true;

        @FXML
        public void zapocniIgru(javafx.event.ActionEvent ae) throws IOException {
        try {
            pokupiPodatke();
        } catch(Exception mse){
             LoggingException.logger.log(Level.SEVERE, mse.fillInStackTrace().toString());
        }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        if(brIgraca == 2) {
        loader.setController(new Controller(xDimenzija, brIgraca, name1, name2));
        }
        else if(brIgraca == 3) {
            loader.setController(new Controller(xDimenzija, brIgraca, name1, name2, name3));
        }
        else {
            loader.setController(new Controller(xDimenzija, brIgraca, name1, name2, name3, name4));
        }
        if(validInput) {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }else {
            validInput = true;
        }
    }
        public void pokupiPodatke() throws MatrixSizeException, NumberOfPlayersException {
        if(Integer.parseInt(dimenzijaX.getText()) > 10 || Integer.parseInt(dimenzijaX.getText()) < 7) {
            showAlertDialog("Pogrešan unos","Dimenzija matrice nije odgovarajuća, mora da bude između 7 i 10.",Alert.AlertType.ERROR);
            clearEarlierInpup();
            throw new MatrixSizeException();
        }
        else {
            xDimenzija = Integer.parseInt(dimenzijaX.getText());
        }
        if(Integer.parseInt(brojIgraca.getText()) > 4 || Integer.parseInt(brojIgraca.getText()) < 2) {
            showAlertDialog("Pogrešan unos","Broj igrača nije odgovarajući, mora da bude između 2 i 4.",Alert.AlertType.ERROR);
            clearEarlierInpup();
            throw new NumberOfPlayersException();
        }
        else{
            brIgraca = Integer.parseInt(brojIgraca.getText());
        }
        try{
            provjeriUnosImena(brIgraca);
        }catch (BadNameInputException bnie) {
            LoggingException.logger.log(Level.SEVERE, bnie.fillInStackTrace().toString());
        }

        }
        private void provjeriUnosImena(int broj) throws BadNameInputException, NumberOfPlayersException{
            if(broj == 2) {
                if(imePrvogIgraca.getText().equals("") || imeDrugogIgraca.getText().equals("") || !(imeTrecegIgraca.getText().equals("")) || !(imeCetvrtogIgraca.getText().equals(""))) {
                    showAlertDialog("Pogrešan unos","Imena igrača nisu unesena na ispravan način.",Alert.AlertType.ERROR);
                    clearEarlierInpup();
                    throw new BadNameInputException();
                }
                else if(imePrvogIgraca.getText().equals(imeDrugogIgraca.getText())){
                    showAlertDialog("Pogrešan unos","Imena igrača ne mogu biti identična.",Alert.AlertType.ERROR);
                    clearEarlierInpup();
                    throw new BadNameInputException();
                }
                else {
                    name1 = imePrvogIgraca.getText();
                    name2 = imeDrugogIgraca.getText();
                    listOfNames.add(name1);
                    listOfNames.add(name2);
                }
            }else if(broj == 3) {
                if(imePrvogIgraca.getText().equals("") || imeDrugogIgraca.getText().equals("") || imeTrecegIgraca.getText().equals("") || !(imeCetvrtogIgraca.getText().equals(""))) {
                    showAlertDialog("Pogrešan unos","Imena igrača nisu unesena na ispravan način.",Alert.AlertType.ERROR);
                    clearEarlierInpup();
                    throw new BadNameInputException();
                }
                else if(imePrvogIgraca.getText().equals(imeDrugogIgraca.getText()) || imePrvogIgraca.getText().equals(imeTrecegIgraca.getText()) || imeDrugogIgraca.getText().equals(imeTrecegIgraca.getText())) {
                    showAlertDialog("Pogrešan unos","Imena igrača ne mogu biti identična.",Alert.AlertType.ERROR);
                    clearEarlierInpup();
                    throw new BadNameInputException();
                }
                else{
                    name1 = imePrvogIgraca.getText();
                    name2 = imeDrugogIgraca.getText();
                    name3 = imeTrecegIgraca.getText();
                    listOfNames.add(name1);
                    listOfNames.add(name2);
                    listOfNames.add(name3);
                }
            }else if(broj == 4) {
                if(imePrvogIgraca.getText().equals("") || imeDrugogIgraca.getText().equals("") || imeTrecegIgraca.getText().equals("") || imeCetvrtogIgraca.getText().equals("")) {
                    showAlertDialog("Pogrešan unos","Imena igrača nisu unesena na ispravan način.",Alert.AlertType.ERROR);
                    clearEarlierInpup();
                    throw new BadNameInputException();
                }
                else if(imePrvogIgraca.getText().equals(imeDrugogIgraca.getText()) || imePrvogIgraca.getText().equals(imeTrecegIgraca.getText()) || imePrvogIgraca.getText().equals(imeCetvrtogIgraca.getText()) ||
                    imeDrugogIgraca.getText().equals(imeTrecegIgraca.getText()) || imeDrugogIgraca.getText().equals(imeCetvrtogIgraca.getText()) ||
                    imeTrecegIgraca.getText().equals(imeCetvrtogIgraca.getText())) {
                    showAlertDialog("Pogrešan unos","Imena igrača ne mogu biti identična.",Alert.AlertType.ERROR);
                    clearEarlierInpup();
                    throw new BadNameInputException();
                }
                else {
                    name1 = imePrvogIgraca.getText();
                    name2 = imeDrugogIgraca.getText();
                    name3 = imeTrecegIgraca.getText();
                    name4 = imeCetvrtogIgraca.getText();
                    listOfNames.add(name1);
                    listOfNames.add(name2);
                    listOfNames.add(name3);
                    listOfNames.add(name4);
                }
            }else throw new NumberOfPlayersException();
        }
        public void showAlertDialog(String nameOfWindow, String message, Alert.AlertType a) {
            Alert alert = new Alert(a);
            alert.setTitle(nameOfWindow);
            alert.setHeaderText(message);
            alert.showAndWait();
            validInput = false;
        }
        public void clearEarlierInpup() {
            dimenzijaX.setText("");
            brojIgraca.setText("");
            imePrvogIgraca.setText("");
            imeDrugogIgraca.setText("");
            imeTrecegIgraca.setText("");
            imeCetvrtogIgraca.setText("");
        }
}
