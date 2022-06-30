package org.unibl.etf.pj2.projektni.model;

public class Figura extends Thread{

    String boja;

    public Figura() {
        super();
    }
    public Figura(String boja) {
        this.boja = boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }
    public String getBoja() {
        return this.boja;
    }
}
