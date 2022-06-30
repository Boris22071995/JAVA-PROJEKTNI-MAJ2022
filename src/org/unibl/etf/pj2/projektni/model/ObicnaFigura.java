package org.unibl.etf.pj2.projektni.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.unibl.etf.pj2.projektni.interfejsi.NacinKretanja;

import java.util.ArrayList;
import java.util.List;

public class ObicnaFigura extends Figura implements NacinKretanja {
    Circle circle = new Circle();
    List<Pane> paneList = new ArrayList<>();
    int dimenzijaMatrice;
    Pane[][] orginalPanes;
    public ObicnaFigura() {
        super();
    }
    public ObicnaFigura(String boja,Pane[][] panes, int dimenzijaMatrice) {
        super(boja);
        this.dimenzijaMatrice = dimenzijaMatrice;
        orginalPanes = panes;
        this.circle.setRadius(10);
        circle.setCenterX(20);
        circle.setCenterY(20);
        if(boja.equals("crvena"))
        this.circle.setFill(Color.RED);
        else if(boja.equals("plava"))
            this.circle.setFill(Color.BLUE);
        else if(boja.equals("zelena"))
            this.circle.setFill(Color.GREEN);
        else
            circle.setFill(Color.YELLOW);
    }
    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public String krecemSe() {
        return "Obicna figura";
    }

    @Override
    public void run() {
        if(dimenzijaMatrice % 2 == 0) dodajUListuParniBrojevi();
        else dodajUListuNeparniBrojevi();
        for(int i = 0; i < paneList.size(); i++) {
            final int x = i;
            Platform.runLater(() -> paneList.get(x).getChildren().add(circle));
            try{
                sleep(1000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void dodajUListuNeparniBrojevi() {
        List<Pomocna> obidjeno = new ArrayList<>();
        int i = 0;
        int dimenzija = dimenzijaMatrice - 1;
        int pola = dimenzija/2;
        int j = pola;
        int temp = 0;
        int polaTemp = pola;
        paneList.add(orginalPanes[j][i]);
       Pomocna p = new Pomocna(j,i);
        obidjeno.add(p);
        do {
            while(++j <= dimenzija) {
                ++i; paneList.add(orginalPanes[j][i]);
                Pomocna a = new Pomocna(j,i);
                obidjeno.add(a); //i=3;j=6
            }
            j--;
            while (--j >= pola) {
                i++; paneList.add(orginalPanes[j][i]);
                Pomocna a = new Pomocna(j,i);
                obidjeno.add(a);
            }
            j++;

            while (--j >= temp) {
                --i; paneList.add(orginalPanes[j][i]);
               Pomocna a = new Pomocna(j,i);
                obidjeno.add(a);
            }
            temp++;
            j++;
            while(++j < polaTemp ) {
                i--; paneList.add(orginalPanes[j][i]);
                Pomocna a = new Pomocna(j,i);
                obidjeno.add(a);
            }
            j--;
            Pomocna pom = new Pomocna(++j,i);
            if(!obidjeno.contains(pom)) {
                paneList.add(orginalPanes[j][i]);
                obidjeno.add(pom);
            }

            dimenzija-=1;

        }while((i != pola || j != pola) && dimenzija != pola);

    }
    public void dodajUListuParniBrojevi() {
        List<Pomocna> obidjeno = new ArrayList<>();
        int dimenzija = dimenzijaMatrice ;
        int pola = dimenzija/2;
        int i = 0;
        int j = pola;
        int temp = 0;
        int polaTemp = pola;
        paneList.add(orginalPanes[j][i]);
        Pomocna p = new Pomocna(j,i);
        obidjeno.add(p);

        do {
            while(++j < dimenzija) {
                ++i; paneList.add(orginalPanes[j][i]);
               Pomocna a = new Pomocna(j,i);
                obidjeno.add(a); //i=3;j=6
            }
            j--;
            while (--j >= pola - 1) {
                i++; paneList.add(orginalPanes[j][i]);
               Pomocna a = new Pomocna(j,i);
                obidjeno.add(a);
            }
            j++;
            while (--j >= temp) {
                --i; paneList.add(orginalPanes[j][i]);
                Pomocna a = new Pomocna(j,i);
                obidjeno.add(a);
            }
            temp++; j++;
            while(++j < polaTemp ) {
                i--; paneList.add(orginalPanes[j][i]);
               Pomocna a = new Pomocna(j,i);
                obidjeno.add(a);
            }
            j--;
            Pomocna pom = new Pomocna(++j,i);
            if(!obidjeno.contains(pom)) {
                paneList.add(orginalPanes[j][i]);
                obidjeno.add(pom);
            }
            dimenzija-=1;
            Pomocna c = new Pomocna(j,i+1);
            if(obidjeno.size() == (dimenzijaMatrice*dimenzijaMatrice)/2 - 1)
                for(int k = 0; k < obidjeno.size();k++) {
                    if(obidjeno.get(k).getX() == c.getX()
                            && obidjeno.get(k).getY() == c.getY()) {
                        paneList.add(orginalPanes[j-1][i+1]);
                        Pomocna a = new Pomocna(j-1,i+1);
                        obidjeno.add(a);
                        dimenzija = pola;
                    }
                }
        }while(dimenzija != pola);
    }
    private static class Pomocna{
        int x;
        int y;
        public Pomocna(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX(){return this.x;}

        public int getY() {return this.y;}
    }


}
