package org.unibl.etf.pj2.projektni.exception;

public class NumberOfHolesException extends Exception{
    public NumberOfHolesException() {
        super("Number of holes are not valid.");
    }
    public NumberOfHolesException(String message) {
        super(message);
    }
}
