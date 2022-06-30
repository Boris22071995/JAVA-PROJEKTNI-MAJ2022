package org.unibl.etf.pj2.projektni.exception;

public class BadNameInputException extends Exception{
    public BadNameInputException() {
        super("Name input are not valid.");
    }
    public BadNameInputException(String message) {
        super(message);
    }
}
