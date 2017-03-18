package com.leo_angelo;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;

/**
 * Created by Angelo on 15/03/2017.
 */
public abstract class Methode {
    protected Plateau plateau; // X0
    protected int fitness;     // f0
    protected int i = 0;       // nb itération

    public Methode(Plateau p) {
        this.plateau = p;
        this.fitness = this.calculFitness(this.plateau.getEchiquierNumber());
        choisirVoisin();
    }

    public abstract void resolve();

    public int calculFitness(int[] colomns) {
        int fit = 0;

        for(int i = 0; i < colomns.length; i++) {
            for(int j = i+1; j < colomns.length; j++) {
                if (Math.abs(colomns[i] - colomns[j]) == (Math.abs(i - j))) {
                    System.out.println("I : " + i + " & J : " + j);
                    fit++;
                }
            }
        }

        return fit;
    }

    public abstract void choisirVoisin();

    public int getNombreVoisins() {
        return (this.plateau.getEchiquier().length-1) * (this.plateau.getEchiquier().length/2);
    }

    public int[][] getVoisins() {
        System.out.println("Listing voisins");
        int[][] voisins = new int[getNombreVoisins()][];
        int cpt = 0;
        for(int i = 0; i < this.plateau.getEchiquier().length; i++) {
            for(int j = i+1; j < this.plateau.getEchiquier().length; j++) {
                voisins[cpt] =  this.plateau.getEchiquierNumber();
                int colonneTemp = voisins[cpt][i];
                voisins[cpt][i] = voisins[cpt][j];
                voisins[cpt][j] = colonneTemp;
                cpt++;
            }
        }

        /*System.out.println("Listing voisins : \n");
        for(int i = 0; i < voisins.length; i++) {
            System.out.println("Voisins " + i + " : [");
            for(int j = 0; j < voisins[i].length; j++) {
                System.out.println(voisins[i][j] + " ");
            }
            System.out.println("]\n\n");
        }*/
        return voisins;
    }
}
