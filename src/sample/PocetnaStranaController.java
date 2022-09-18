package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.unibl.etf.pj2.projektni.exception.*;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class PocetnaStranaController {



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
    int yDimenzija;
    int brIgraca;
    String ime1;
    String ime2;
    String ime3;
    String ime4;
    List<String> listOfNames = new ArrayList<>();


    @FXML
    public void zapocniIgru(javafx.event.ActionEvent ae) throws IOException {

        try {
            pokupiPodatke();
        }catch(MatrixSizeException mse){
             LoggingException.logger.log(Level.SEVERE, mse.fillInStackTrace().toString());
        }catch (NumberOfPlayersException nope) {
             LoggingException.logger.log(Level.SEVERE, nope.fillInStackTrace().toString());
        }catch(FileNotFoundException fnfe) {
             LoggingException.logger.log(Level.SEVERE, fnfe.fillInStackTrace().toString());
        }catch (Exception e) {
             LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }


        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        if(brIgraca == 2) {
        loader.setController(new Controller(xDimenzija, brIgraca, ime1, ime2));
        }
        else if(brIgraca == 3) {
            loader.setController(new Controller(xDimenzija, brIgraca, ime1, ime2, ime3));
        }
        else {
            loader.setController(new Controller(xDimenzija, brIgraca, ime1, ime2, ime3, ime4));
        }

        Parent root = loader.load();
        Scene scene= new Scene(root);
        Stage window = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

        public void pokupiPodatke() throws MatrixSizeException, NumberOfPlayersException, IOException {
        if(Integer.parseInt(dimenzijaX.getText()) > 10 || Integer.parseInt(dimenzijaX.getText()) < 7) throw new MatrixSizeException();
        else {
            xDimenzija = Integer.parseInt(dimenzijaX.getText());
        }
        if(Integer.parseInt(brojIgraca.getText()) > 4 || Integer.parseInt(brojIgraca.getText()) < 2)  throw new NumberOfPlayersException();
        else{
            brIgraca = Integer.parseInt(brojIgraca.getText());
        }
        try{
            provjeriUnosImena(brIgraca);
        }catch (BadNameInputException bnie) {
            LoggingException.logger.log(Level.SEVERE, bnie.fillInStackTrace().toString());
        }

        }
        private void provjeriUnosImena(int broj) throws BadNameInputException, NumberOfPlayersException {
            if(broj == 2) {
                if(imePrvogIgraca.getText().equals("") || imeDrugogIgraca.getText().equals("") || !(imeTrecegIgraca.getText().equals("")) || !(imeCetvrtogIgraca.getText().equals(""))) {
                    throw new BadNameInputException();
                }
                else if(imePrvogIgraca.getText().equals(imeDrugogIgraca.getText())) throw new BadNameInputException();
                else {
                    ime1 = imePrvogIgraca.getText();
                    ime2 = imeDrugogIgraca.getText();
                    listOfNames.add(ime1);
                    listOfNames.add(ime2);
                }
            }else if(broj == 3) {
                if(imePrvogIgraca.getText().equals("") || imeDrugogIgraca.getText().equals("") || imeTrecegIgraca.getText().equals("") || !(imeCetvrtogIgraca.getText().equals(""))) {
                    throw new BadNameInputException();
                }
                else if(imePrvogIgraca.getText().equals(imeDrugogIgraca.getText()) || imePrvogIgraca.getText().equals(imeTrecegIgraca.getText()) || imeDrugogIgraca.getText().equals(imeTrecegIgraca.getText())) throw new BadNameInputException();
                else{
                    ime1 = imePrvogIgraca.getText();
                    ime2 = imeDrugogIgraca.getText();
                    ime3 = imeTrecegIgraca.getText();
                    listOfNames.add(ime1);
                    listOfNames.add(ime2);
                    listOfNames.add(ime3);
                }
            }else if(broj == 4) {
                if(imePrvogIgraca.getText().equals("") || imeDrugogIgraca.getText().equals("") || imeTrecegIgraca.getText().equals("") || imeCetvrtogIgraca.getText().equals("")) {
                    throw new BadNameInputException();
                }
                else if(imePrvogIgraca.getText().equals(imeDrugogIgraca.getText()) || imePrvogIgraca.getText().equals(imeTrecegIgraca.getText()) || imePrvogIgraca.getText().equals(imeCetvrtogIgraca.getText()) ||
                    imeDrugogIgraca.getText().equals(imeTrecegIgraca.getText()) || imeDrugogIgraca.getText().equals(imeCetvrtogIgraca.getText()) ||
                    imeTrecegIgraca.getText().equals(imeCetvrtogIgraca.getText())) throw new BadNameInputException();
                else {
                    ime1 = imePrvogIgraca.getText();
                    ime2 = imeDrugogIgraca.getText();
                    ime3 = imeTrecegIgraca.getText();
                    ime4 = imeCetvrtogIgraca.getText();
                    listOfNames.add(ime1);
                    listOfNames.add(ime2);
                    listOfNames.add(ime3);
                    listOfNames.add(ime4);
                }
            }else throw new NumberOfPlayersException();
        }



}
