package com.leo_angelo.Algorithme;

/**
 * Created by Angelo on 15/03/2017.
 */
public class RecuitSimule extends Methode {

    public RecuitSimule(Plateau p) {
        super(p);
    }

    public void resolve() {

    }

    @Override
    public void choisirVoisin() {
        getVoisins();
        int choix = (int) (Math.random() * (this.listeVoisins.length));
        System.out.println("CHOIX " + choix);
        Plateau voisinChoisi = new Plateau(this.listeVoisins[choix]);
        System.out.println(voisinChoisi);
        System.out.println("Fitness voisin choisi : " + calculFitness(voisinChoisi.getEchiquier()));
    }

}
