package com.leo_angelo.Algorithme;

import com.leo_angelo.Vue.PlateauGraph;
import com.leo_angelo.Vue.TabouVue;

/**
 * Created by Angelo on 15/03/2017.
 */
public class Tabou extends Methode {

    private int[] fitnessVoisins;
    private int[] listeTabou;

    private int indexTabou = 0;
    private int taille = 3;

    private TabouVue tabouVue;

    public Tabou(Plateau p) {
        super(p);
        init();

        tabouVue = new TabouVue(this.plateau, this);
        tabouVue.pack();
        tabouVue.setVisible(true);
    }

    @Override
    public void init() {
        super.init();
        this.listeTabou = new int[taille];
    }

    public void setParam(int tailleListe, Plateau p, int nbIteration) {
        this.plateau = p;
        this.taille = tailleListe;
        this.nombreIteration = nbIteration;
    }

    public void resolve() {
        init();
        int i = 0;
        while(i<nombreIteration && fitness != 0) {
            i++;
            tabouVue.updateChart(fitness);
            System.out.println("Itération " + i);
            choisirVoisin();
        }
        tabouVue.updateChart(fitness);
        /*this.grille = new PlateauGraph(this.plateau);
        grille.pack();
        grille.setVisible(true);*/
    }


    @Override
    public void choisirVoisin() {
        getVoisins();
        this.fitnessVoisins = new int[this.listeVoisins.length];
        for(int i=0; i<this.listeVoisins.length; i++) {
            this.fitnessVoisins[i] = calculFitness(this.listeVoisins[i]);
            //System.out.println("Voisin " + i + " has fitness " + this.fitnessVoisins[i]);
        }
        int iMeilleurVoisin = findMaximumFitness();
        System.out.println("Meilleur voisin : " + iMeilleurVoisin + " has fitness " + this.fitnessVoisins[iMeilleurVoisin]);

        if(this.fitnessVoisins[iMeilleurVoisin] >= this.fitness) {
            System.out.println("Ajout du voisin " + iMeilleurVoisin + " en tabou !!");
            ajouterTabou(iMeilleurVoisin);
        }
        this.plateau = new Plateau(listeVoisins[iMeilleurVoisin]);
        this.fitness = this.fitnessVoisins[iMeilleurVoisin];
    }

    public int findMaximumFitness() {
        int imax = 0;
        for(int i=1; i<this.fitnessVoisins.length; i++) {
            if(this.fitnessVoisins[i] < this.fitnessVoisins[imax] && !estTabou(i)) {
                imax = i;
            }
        }
        return imax;
    }

    public boolean estTabou(int voisinATester) {
        if(listeTabou != null) {
            for (int i=0; i<listeTabou.length; i++) {
                if(listeTabou[i] == voisinATester) return true;
            }
        }
        return false;
    }

    public void ajouterTabou(int voisinTabou) {
        listeTabou[this.indexTabou] = voisinTabou;
        if(this.indexTabou >= taille - 1) this.indexTabou = 0;
        else this.indexTabou++;
    }
}