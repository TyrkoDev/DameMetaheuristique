package com.leo_angelo;

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
        this.voisinChoisi = new Plateau(this.listeVoisins[choix]);
        System.out.println(this.voisinChoisi);
       this.fitnessVoisinChoisi = calculFitness(this.voisinChoisi.getEchiquier());
        System.out.println("Fitness voisin choisi : " + this.fitnessVoisinChoisi);
    }

}
