package com.leo_angelo;

/**
 * Created by Angelo on 15/03/2017.
 */
public class RecuitSimule extends Methode {

    public RecuitSimule(Plateau p) {
        super(p);
    }

    @Override
    public void resolve() {
        calculFitness(this.plateau.getEchiquierNumber());
    }

    @Override
    public void choisirVoisin() {
        int[][] voisins = getVoisins();
        int choix = (int) (Math.random() * (voisins.length));
        System.out.println("CHOIX " + choix);
        Plateau plateau = new Plateau(voisins[choix]);
        System.out.println(plateau);
        System.out.println("Fitness voisin : " + calculFitness(voisins[choix]));
    }

}
