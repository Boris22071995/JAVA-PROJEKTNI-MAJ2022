package org.unibl.etf.pj2.projektni.exception;

public class NameAllreadyExistException extends Exception{
    public NameAllreadyExistException() {
        super("Player with this name allready exist, you need to use another name.");
    }
    public NameAllreadyExistException(String message) {
        super(message);
    }
}
