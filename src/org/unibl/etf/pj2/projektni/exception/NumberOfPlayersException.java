package org.unibl.etf.pj2.projektni.exception;

public class NumberOfPlayersException extends Exception{

    public NumberOfPlayersException() {
        super("Broj igrača nije unesen na ispravan način.");
    }

    public  NumberOfPlayersException(String message) {
        super(message);
    }

}
