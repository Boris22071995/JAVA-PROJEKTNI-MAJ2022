package org.unibl.etf.pj2.projektni.exception;

public class NumberOfPlayersException extends Exception{
    public NumberOfPlayersException() {
        super("Number of players are not valid.");
    }
    public  NumberOfPlayersException(String message) {
        super(message);
    }
}
