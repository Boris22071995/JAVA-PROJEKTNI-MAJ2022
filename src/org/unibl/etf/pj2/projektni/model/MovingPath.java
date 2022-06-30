package org.unibl.etf.pj2.projektni.model;

import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

public class MovingPath {

    Pane[][] panes;
    int matrixDimension;
    List<Pane> paneList = new ArrayList<>();

    public MovingPath(Pane[][] panes, int matrixDimension) {
        this.matrixDimension = matrixDimension;
        this.panes = panes;
    }
    public void addToListOddNumber() {
        List<HelpClass> obidjeno = new ArrayList<>();
        int i = 0;
        int dimenzija = matrixDimension - 1;
        int pola = dimenzija/2;
        int j = pola;
        int temp = 0;
        paneList.add(panes[j][i]);
        HelpClass hp = new HelpClass(j,i);
        obidjeno.add(hp);

        do {
            while(++j <= dimenzija) {
                ++i; paneList.add(panes[j][i]);
                HelpClass a = new HelpClass(j,i);
                obidjeno.add(a);
            }
            j--;
            while (--j >= pola) {
                i++; paneList.add(panes[j][i]);
                HelpClass a = new HelpClass(j,i);
                obidjeno.add(a);
            }
            j++;
            while (--j >= temp) {
                --i; paneList.add(panes[j][i]);
                HelpClass a = new HelpClass(j,i);
                obidjeno.add(a);
            }
            temp++;
            j++;
            while(++j < pola) {
                i--; paneList.add(panes[j][i]);
                HelpClass a = new HelpClass(j,i);
                obidjeno.add(a);
            }
            j--;
            HelpClass pom = new HelpClass(++j,i);
            if(!obidjeno.contains(pom)) {
                paneList.add(panes[j][i]);
                obidjeno.add(pom);
            }
            dimenzija-=1;
        } while((i != pola || j != pola) && dimenzija != pola);
    }
    public void addToListEvenNumber() {
        List<HelpClass> obidjeno = new ArrayList<>();
        int dimenzija = matrixDimension;
        int pola = dimenzija/2;
        int i = 0;
        int j = pola;
        int temp = 0;
        paneList.add(panes[j][i]);
        HelpClass p = new HelpClass(j,i);
        obidjeno.add(p);

        do {
            while(++j < dimenzija) {
                ++i; paneList.add(panes[j][i]);
                HelpClass a = new HelpClass(j,i);
                obidjeno.add(a); //i=3;j=6
            }
            j--;
            while (--j >= pola - 1) {
                i++; paneList.add(panes[j][i]);
                HelpClass a = new HelpClass(j,i);
                obidjeno.add(a);
            }
            j++;
            while (--j >= temp) {
                --i; paneList.add(panes[j][i]);
                HelpClass a = new HelpClass(j,i);
                obidjeno.add(a);
            }
            temp++; j++;
            while(++j < pola) {
                i--; paneList.add(panes[j][i]);
                HelpClass a = new HelpClass(j,i);
                obidjeno.add(a);
            }
            j--;
            HelpClass pom = new HelpClass(++j,i);
            if(!obidjeno.contains(pom)) {
                paneList.add(panes[j][i]);
                obidjeno.add(pom);
            }
            dimenzija-=1;
            HelpClass c = new HelpClass(j,i+1);
            if(obidjeno.size() == (matrixDimension * matrixDimension)/2 - 1)
                for(int k = 0; k < obidjeno.size();k++) {
                    if(obidjeno.get(k).getX() == c.getX()
                            && obidjeno.get(k).getY() == c.getY()) {
                        paneList.add(panes[j-1][i+1]);
                        HelpClass a = new HelpClass(j-1,i+1);
                        obidjeno.add(a);
                        dimenzija = pola;
                    }
                }
        }while(dimenzija != pola);
    }
    public List<Pane> getPaneList() {
        return this.paneList;
    }
    private static class HelpClass {
        int x;
        int y;
        public HelpClass(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int getX(){return this.x;}

        public int getY() {return this.y;}
    }
}
