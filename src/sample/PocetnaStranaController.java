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
import org.unibl.etf.pj2.projektni.exception.BadNameInputException;
import org.unibl.etf.pj2.projektni.exception.MatrixSizeException;
import org.unibl.etf.pj2.projektni.exception.NameAllreadyExistException;
import org.unibl.etf.pj2.projektni.exception.NumberOfPlayersException;


import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.*;

public class PocetnaStranaController {

    public static Handler handler;
    public static Logger logger;
    static {
        try {
            handler = new FileHandler("izuzeci.log",true);
            logger = Logger.getLogger(PocetnaStranaController.class.getName());
            logger.addHandler(handler);
            handler.setFormatter(new SimpleFormatter());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

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


    @FXML
    public void zapocniIgru(javafx.event.ActionEvent ae) throws IOException {
        File file = new File("igraci.txt");
        if(!file.exists()) file.createNewFile();

        try {
            pokupiPodatke();
        }catch(MatrixSizeException mse){
             PocetnaStranaController.logger.log(Level.SEVERE, mse.fillInStackTrace().toString());
        }catch (NumberOfPlayersException nope) {
            PocetnaStranaController.logger.log(Level.SEVERE, nope.fillInStackTrace().toString());
        }catch(FileNotFoundException fnfe) {
            PocetnaStranaController.logger.log(Level.SEVERE, fnfe.fillInStackTrace().toString());
        }catch (Exception e) {
            PocetnaStranaController.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
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
            PocetnaStranaController.logger.log(Level.SEVERE, bnie.fillInStackTrace().toString());
        }
        try{
            popuniFajl(brIgraca);
        }catch (NumberOfPlayersException nope) {
            PocetnaStranaController.logger.log(Level.SEVERE, nope.fillInStackTrace().toString());
        }

        }
        private void popuniFajl(int broj) throws IOException, NumberOfPlayersException {
            File file = new File("igraci.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            if(broj == 2) {
                bw.write(ime1);
                bw.write("\n");
                bw.write(ime2);
                bw.write("\n");
            }else if(broj == 3) {
                bw.write(ime1);
                bw.write("\n");
                bw.write(ime2);
                bw.write("\n");
                bw.write(ime3);
                bw.write("\n");
            }else if(broj == 4) {
                bw.write(ime1);
                bw.write("\n");
                bw.write(ime2);
                bw.write("\n");
                bw.write(ime3);
                bw.write("\n");
                bw.write(ime4);
                bw.write("\n");
            }else throw new NumberOfPlayersException();
            bw.close();
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
                }
            }else throw new NumberOfPlayersException();
        }



}
