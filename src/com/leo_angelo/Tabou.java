package com.leo_angelo;

import java.util.ArrayList;

/**
 * Created by Angelo on 15/03/2017.
 */
public class Tabou extends Methode {

    private int[] fitnessVoisins;
    private int[][] listeTabou = new int[3][];

    private int indexTabou = 0;

    public Tabou(Plateau p) {
        super(p);
    }

    @Override
    public void resolve() {

    }

    @Override
    public void choisirVoisin() {
        getVoisins();
        this.fitnessVoisins = new int[this.listeVoisins.length];
        for(int i=0; i<this.listeVoisins.length; i++) {
            this.fitnessVoisins[i] = calculFitness(this.listeVoisins[i]);
            System.out.println("Voisin " + i + " has fitness " + this.fitnessVoisins[i]);
        }
        int iMeilleurVoisin = findMaximumFitness();
        this.voisinChoisi = new Plateau(this.listeVoisins[iMeilleurVoisin]);
        this.fitnessVoisinChoisi = this.fitnessVoisins[iMeilleurVoisin];
        System.out.println("Meilleur voisin : " + iMeilleurVoisin);

        if(this.fitnessVoisinChoisi > this.fitness) {
            ajouterTabou(listeVoisins[iMeilleurVoisin]);
        }
        this.plateau = new Plateau(listeVoisins[iMeilleurVoisin]);
        System.out.println("Nouveau plateau: ");
        System.out.println(this.plateau);
    }

    public int findMaximumFitness() {
        int imax = 0;
        for(int i=1; i<this.fitnessVoisins.length; i++) {
            if(this.fitnessVoisins[i] < this.fitnessVoisins[imax] && !estTabou(listeVoisins[i])) {
                imax = i;
            }
        }
        return imax;
    }

    public boolean estTabou(int[] plateauATester) {
        if(listeTabou != null) {
            for (int i=0; i<listeTabou.length; i++) {
                if(listeTabou[i] == plateauATester) return true;
            }
        }
        return false;
    }

    public void ajouterTabou(int[] plateau) {
        listeTabou[this.indexTabou] = plateau;
        if(this.indexTabou == 2) this.indexTabou = 0;
        else this.indexTabou++;
    }
}