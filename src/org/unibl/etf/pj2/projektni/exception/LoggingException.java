package org.unibl.etf.pj2.projektni.exception;

import sample.PocetnaStranaController;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingException {

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
}
