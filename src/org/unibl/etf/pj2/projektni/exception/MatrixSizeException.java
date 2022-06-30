package org.unibl.etf.pj2.projektni.exception;

public class MatrixSizeException extends Exception{

    public MatrixSizeException() {
        super("Dimenzije matrice nisu odgovarajuće.");
    }

    public MatrixSizeException(String message) {
        super(message);
    }
}
