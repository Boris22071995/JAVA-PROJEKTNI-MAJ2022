package org.unibl.etf.pj2.projektni.exception;

public class MatrixSizeException extends Exception{
    public MatrixSizeException() {
        super("Matrix dimension is not valid.");
    }
    public MatrixSizeException(String message) {
        super(message);
    }
}
