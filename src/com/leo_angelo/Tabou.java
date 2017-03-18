package com.leo_angelo;

/**
 * Created by Angelo on 15/03/2017.
 */
public class Tabou extends Methode {
    public Tabou(Plateau p) {
        super(p);
    }

    @Override
    public void resolve() {

    }

    @Override
    public void choisirVoisin() {
        getVoisins();
        int[] fitnessVoisins = new int[this.listeVoisins.length];
        for(int i=0; i<this.listeVoisins.length; i++) {
            fitnessVoisins[i] = calculFitness(this.listeVoisins[i]);
            System.out.println("Voisin " + i + " has fitness " + fitnessVoisins[i]);
        }
    }

    public int findMaximum() {
        return 0;
    }
}
