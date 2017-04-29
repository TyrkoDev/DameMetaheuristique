package com.leo_angelo.Algorithme;

import com.leo_angelo.Vue.PlateauGraph;

/**
 * Created by Angelo on 15/03/2017.
 */
public abstract class Methode {
    protected PlateauGraph grille;

    protected Plateau plateau; // X0
    protected int fitness;     // f0
    protected int[][] listeVoisins;
    protected int nombreVoisin;
    protected int nombreIteration = 10;

    public Methode(Plateau p) {
        this.plateau = p;
        this.fitness = this.calculFitness(this.plateau.getEchiquier());
        System.out.println("Fitness du plateau initial : " + this.fitness);
        this.nombreVoisin = getNombreVoisins();
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
        this.listeVoisins = new int[this.nombreVoisin][];
        int cpt = 0;
        for(int i = 0; i < this.plateau.getEchiquier().length; i++) {
            for(int j = i+1; j < this.plateau.getEchiquier().length; j++) {
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
