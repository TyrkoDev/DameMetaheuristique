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
    protected Plateau voisinChoisi;
    protected int fitnessVoisinChoisi;
    protected int[][] listeVoisins;

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

    public void getVoisins() {
        System.out.println("Listing voisins");
        this.listeVoisins = new int[getNombreVoisins()][];
        int cpt = 0;
        for(int i = 0; i < this.plateau.getEchiquier().length; i++) {
            for(int j = i+1; j < this.plateau.getEchiquier().length; j++) {
                listeVoisins[cpt] =  this.plateau.getEchiquierNumber();
                int colonneTemp = listeVoisins[cpt][i];
                listeVoisins[cpt][i] = listeVoisins[cpt][j];
                listeVoisins[cpt][j] = colonneTemp;
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
    }
}
