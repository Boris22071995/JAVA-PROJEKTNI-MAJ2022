package org.unibl.etf.pj2.projektni.exception;

public class BadNameInputException extends Exception{
    public BadNameInputException() {
        super("Popunjavanje polja sa imenima nije validno.");
    }
    public BadNameInputException(String message) {
        super(message);
    }
}
