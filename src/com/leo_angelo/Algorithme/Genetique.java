package com.leo_angelo.Algorithme;

/**
 * Created by Angelo on 12/05/2017.
 */
public class Genetique extends Methode{
    private int nombre_generation = 0;

    public Genetique(Plateau plateau) {
        super(plateau);
    }

    /**
     * Altération aléatoire de quelques solutions
     *          Prendre la meilleure solution (meilleure fitness) et changé aléatoirement des positions
     *          de dames sur le plateau
     */
    public void mutation() {

    }

    /**
     * Recombinaison des solutions reproduites
     *          Intervertir les positions des dames d'un sous-ensemble de 2 solutions
     *          ex : 0011|01100 -->  0011|00011
     *               1001|00011 -->  1001|01100
     */
    public void croisement() {

    }

    /**
     * Séléction de certaines solutions
     *          Prendre aléatoirement une solution voisine
     */
    public void reproduction() {

    }

    @Override
    public void resolve() {
        while(calculFitness(plateau.getEchiquier()) != 0) {

        }
    }

    @Override
    public void choisirVoisin() {
        try {
            throw new Exception("Inutilisable sur l'algorithme génétique !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
