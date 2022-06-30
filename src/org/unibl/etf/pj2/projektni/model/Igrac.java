package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;

import java.util.Random;

public class Igrac {

    String ime;
    String boja;
    Figura[] figure = new Figura[4];
    Pane[][] orginalPane;
    int dimenzijaMatrice;

    public Igrac() {
        super();
    }
    public Igrac(String ime, String boja, Pane[][] panes, int dimenzijaMatrice) {
        this.ime = ime;
        this.boja = boja;
        this.orginalPane = panes;
        this.dimenzijaMatrice = dimenzijaMatrice;
        dodajFigure();
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }

    public Figura[] getFigure() {
        return figure;
    }

    public void setFigure(Figura[] figure) {
        this.figure = figure;
    }

    public void dodajFigure() {
        Random rand = new Random();
        for(int i = 0; i < 4; i++) {
            int br = rand.nextInt(3);
            if(br == 0) {
                ObicnaFigura of = new ObicnaFigura(boja, orginalPane, dimenzijaMatrice);
                figure[i]  = of;
            }
            else if(br == 1) {
                LebdecaFigura lf = new LebdecaFigura(boja, orginalPane, dimenzijaMatrice);
                figure[i] = lf;
            }
            else {
                SuperBrzaFigura sbf = new SuperBrzaFigura(boja, orginalPane,dimenzijaMatrice);
            }
        }
    }

}
