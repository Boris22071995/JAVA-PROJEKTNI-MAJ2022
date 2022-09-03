package org.unibl.etf.pj2.projektni.model;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MovingPath {

    Pane[][] panes;
    int matrixDimension;
    List<Pane> paneList = new ArrayList<>();
    ArrayList<Label> labels;
    ArrayList<Label> labelsOnPath;

    int[][] matrixOfNumbers;
    List<Integer> numbers = new ArrayList<>();
    int br = 1;

    public MovingPath(Pane[][] panes, int matrixDimension, ArrayList<Label> labels) {
        this.matrixDimension = matrixDimension;
        this.panes = panes;
        this.labels = labels;
        matrixOfNumbers = new int[matrixDimension][matrixDimension];
        for(int i = 0; i < matrixDimension; i++)
            for(int j = 0; j < matrixDimension; j++) {
                matrixOfNumbers[i][j] = br++;
            }
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
        for(int k = 0; k < obidjeno.size();k++){
            numbers.add(matrixOfNumbers[obidjeno.get(k).getY()][obidjeno.get(k).getX()]);
        }
        processNumbers();
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
        for(int k = 0; k < obidjeno.size();k++){
            numbers.add(matrixOfNumbers[obidjeno.get(k).getY()][obidjeno.get(k).getX()]);
        }
        processNumbers();
    }
    public List<Pane> getPaneList() {
        return this.paneList;
    }
    public void processNumbers() {
        List<Integer> temp = new ArrayList<>();
        for(Integer t : numbers) {
            if(!temp.contains(t)){
                temp.add(t);
            }
        }
        numbers = temp;
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
    public List<Integer> getNumbers() {
        return numbers;
    }
}
