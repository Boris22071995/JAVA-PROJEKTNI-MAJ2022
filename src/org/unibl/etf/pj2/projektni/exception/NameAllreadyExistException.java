package org.unibl.etf.pj2.projektni.exception;

public class NameAllreadyExistException extends Exception{
    public NameAllreadyExistException() {
        super("Igrac sa ovim imenom vec postoji, morate unijeti drugo ime.");
    }
    public NameAllreadyExistException(String message) {
        super(message);
    }
}
