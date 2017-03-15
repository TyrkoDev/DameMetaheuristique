package com.leo_angelo;

/**
 * Created by Angelo on 15/03/2017.
 */
public abstract class Methode {
    protected Plateau plateau; // X0
    protected int fitness;     // f0
    protected int i = 0;       // nb itération

    public Methode(Plateau p) {
        this.plateau = p;
        this.fitness = this.calculFitness();
    }

    public abstract void resolve();

    public int calculFitness() {
        int fit = 0;
        Colomn[] colomns = this.plateau.getEchiquier();

        for(int i = 0; i < colomns.length; i++) {
            for(int j = i+1; j < colomns.length; j++) {
                if (Math.abs(colomns[i].getNum() - colomns[j].getNum()) == (Math.abs(i - j))) {
                    System.out.println("I : " + i + " & J : " + j);
                    fit++;
                }
            }
        }

        return fit;
    }

    public void choisirVoisin() {

    }
}
