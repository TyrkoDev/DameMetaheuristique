package com.leo_angelo;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;

/**
 * Created by Angelo on 15/03/2017.
 */
public abstract class Methode {
    protected Plateau plateau; // X0
    protected int fitness;     // f0
    protected Plateau voisinChoisi;
    protected int fitnessVoisinChoisi;
    protected int[][] listeVoisins;
    protected int nombreVoisin;

    public Methode(Plateau p) {
        this.plateau = p;
        this.fitness = this.calculFitness(this.plateau.getEchiquier());
        this.nombreVoisin = getNombreVoisins();
        choisirVoisin();
        //System.out.println("Nombre de voisins : " + getNombreVoisins());
        //getVoisins();
    }

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
        System.out.println("Listing voisins...");
        this.listeVoisins = new int[this.nombreVoisin][];
        int cpt = 0;
        for(int i = 0; i < this.plateau.getEchiquier().length; i++) {
            for(int j = i+1; j < this.plateau.getEchiquier().length; j++) {
                //System.out.println("Voisin " + cpt + ", remplacement de la colonne " + this.plateau.getEchiquier()[i] + " avec la colonne " + this.plateau.getEchiquier()[j]);
                //System.out.println("I : " + i + " - J : " + j);
                //System.out.println("val I : " + this.plateau.getEchiquier()[i] + " - val J : " + this.plateau.getEchiquier()[j]);
                this.listeVoisins[cpt] =  this.plateau.getEchiquier();
                int colonneTemp = this.listeVoisins[cpt][i];
                this.listeVoisins[cpt][i] = this.listeVoisins[cpt][j];

                this.listeVoisins[cpt][j] = colonneTemp;

                cpt++;
            }
        }

/*        for(int i = 0; i < listeVoisins.length; i++) {
            System.out.println("Voisins " + i + " : [");
            for(int j = 0; j < listeVoisins[i].length; j++) {
                System.out.println(listeVoisins[i][j] + " ");
            }
            System.out.println("]\n\n");
        }*/
    }
}
