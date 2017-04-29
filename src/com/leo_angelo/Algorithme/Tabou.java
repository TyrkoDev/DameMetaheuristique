package com.leo_angelo.Algorithme;

import com.leo_angelo.Vue.PlateauGraph;

/**
 * Created by Angelo on 15/03/2017.
 */
public class Tabou extends Methode {

    private int[] fitnessVoisins;
    private int[] listeTabou;

    private int indexTabou = 0;

    public Tabou(Plateau p) {
        super(p);
        this.listeTabou = new int[3];
        resolve();
    }

    public void resolve() {
        int i = 0;
        while(i<nombreIteration && fitness != 0) {
            i++;
            System.out.println("Itération " + i);
            choisirVoisin();
        }
        this.grille = new PlateauGraph(this.plateau);
        grille.pack();
        grille.setVisible(true);
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
        System.out.println("Nouveau plateau: ");
        System.out.println(this.plateau);

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
        if(this.indexTabou >= 2) this.indexTabou = 0;
        else this.indexTabou++;
    }
}