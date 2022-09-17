package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.control.Label;
import org.unibl.etf.pj2.projektni.exception.LoggingException;

import java.awt.*;
import java.sql.Time;
import java.util.logging.Level;

public class MyTimer extends Thread{
    Label timeLabel;
    int second = 0;
    boolean going = true;
    public MyTimer(Label timeLabel) {
        this.timeLabel = timeLabel;
    }

    @Override
    public void run() {
        while(going) {
            Platform.runLater(()-> timeLabel.setText("Vrijeme trajanja igre: " + second++));
            try{
                sleep(1000);
            }catch (InterruptedException e) {
                LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
            }
        }
    }
    public int getSecond() {
        return this.second;
    }

}
