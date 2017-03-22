package com.leo_angelo;

import java.util.ArrayList;

/**
 * Created by Angelo on 15/03/2017.
 */
public class Plateau {
    private int[] echiquier;

    private int size = 0;

    public Plateau(int n) {
        this.size = n;

        //int[] listeColonne = new int[n];
        ArrayList<Integer> listeColonne = new ArrayList<>();

        for(int i = 0; i < this.size; i++) {
            //listeColonne[i] = i;
            listeColonne.add(i);
        }

        this.echiquier = new int[n];
        for(int i = 0; i < this.size; i++) {
            int choix = (int) (Math.random() * (listeColonne.size()));
            this.echiquier[i] = listeColonne.get(choix);
            listeColonne.remove(choix);
        }
    }

    public Plateau(int[] colomns) {
        this.size = colomns.length;
        this.echiquier = new int[this.size];
        for(int i = 0; i < this.size; i++) {
            this.echiquier[i] = colomns[i];
        }
    }

    public int[] getEchiquier() {
        return this.echiquier;
    }

    /*public int[] getEchiquierNumber() {
        int[] echiquierNumber = new int[this.echiquier.length];
        for(int i=0; i<this.echiquier.length; i++) {
            echiquierNumber[i] = this.echiquier[i].getNum();
        }
        return echiquierNumber;
    }*/

    public String toString() {
        StringBuilder stb = new StringBuilder();
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                stb.append((echiquier[i] == j ? 1 : 0) + " ");
            }
            stb.append("\n");
        }

        return stb.toString();
    }
}
