package org.unibl.etf.pj2.projektni.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ResultProcessing {
    String path;
    List<Player> listOfPlayers = new ArrayList<>();
    boolean isDone = false;

    public ResultProcessing(){
        super();
    }
    public void processing() {
        if(isDone == false) {
        int p = new File(System.getProperty("user.dir") + File.separator + "rezultati").list().length;
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH-mm");
        LocalDateTime dateAndTime = LocalDateTime.now();
        String temp = "IGRA_"+formatTime.format(dateAndTime)+".txt";
        //formatTime.format(dateAndTime);
        String path = System.getProperty("user.dir") + File.separator + "rezultati" + File.separator + temp;
        File file = new File(path);
        try {

               file.createNewFile();

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(int i = 0; i < listOfPlayers.size(); i++) {
                bufferedWriter.write("Igrač " + (i+1) + " - " + listOfPlayers.get(i).getNames()+"\n");
                List<Figure> figureList = listOfPlayers.get(0).getFigure();
                for(int j = 0; j < 4; j++) {
                    bufferedWriter.write("\t" + "Figura " + (j + 1) + "("+figureList.get(j).move()+", " + listOfPlayers.get(i).getColour() + ")" + "\n");
                }
            }
            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        isDone = true;
        }
    }
    public void addPlayerToList(Player p) {
        if(!listOfPlayers.contains(p)) listOfPlayers.add(p);
    }
}
