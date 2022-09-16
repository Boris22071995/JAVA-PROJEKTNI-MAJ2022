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
    MovingPath mp;
    int timeOfPlay;

    List<Integer> numbersOfProcessedPath = new ArrayList<>();
    public ResultProcessing(MovingPath mp){
        this.mp = mp;
        numbersOfProcessedPath = mp.getNumbers();
    }
    public void setTimeOfPlay(int time) {
        this.timeOfPlay = time;
    }
    public void processing() {
        if(isDone == false) {
        int p = new File(System.getProperty("user.dir") + File.separator + "rezultati").list().length;
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH-mm");
        LocalDateTime dateAndTime = LocalDateTime.now();
        String temp = "IGRA_"+formatTime.format(dateAndTime)+".txt";
        String path = System.getProperty("user.dir") + File.separator + "rezultati" + File.separator + temp;
        File file = new File(path);
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(int i = 0; i < listOfPlayers.size(); i++) {
                bufferedWriter.write("Igrač " + (i+1) + " - " + listOfPlayers.get(i).getNames()+"\n");
                List<Figure> figureList = listOfPlayers.get(i).getListOfFiguresForResults();
                for(int j = 0; j < 4; j++) {
                    Figure f = figureList.get(j);
                    bufferedWriter.write("\t" + "Figura " + (j + 1) + "("+f.move()+", " + listOfPlayers.get(i).getColour() + ")" + " - " + "pređeni put (");
                    for(int k = 0; k < f.getProcessedPath().size(); k++) {
                        if(k!=f.getProcessedPath().size() - 1) {
                            bufferedWriter.write(numbersOfProcessedPath.get(k) + "-");
                        }else {
                            bufferedWriter.write(numbersOfProcessedPath.get(k) + ") - stigla do cilja (");
                        }
                    }
                    if(f.getProcessedPath().size() == mp.getPaneList().size()) {
                        bufferedWriter.write("DA) \n");
                    }else {
                        bufferedWriter.write("NE) \n");
                    }
                }
            }
            bufferedWriter.write("Ukupno vrijeme trajanje igre: " + timeOfPlay);
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
