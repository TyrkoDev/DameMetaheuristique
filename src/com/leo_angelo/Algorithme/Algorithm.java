package com.leo_angelo.Algorithme;

/**
 * Created by Angelo on 21/05/2017.
 */
public abstract class Algorithm {
    protected int fitness;     // f0
    protected int iterationNumber = 10;

    public abstract void initialisation();

    public abstract void resolve();

    public int getFitness(int[] colomns) {
        int fitness = 0;

        for(int i = 0; i < colomns.length; i++) {
            for(int j = i+1; j < colomns.length; j++) {
                if (Math.abs(colomns[i] - colomns[j]) == (Math.abs(i - j))) {
                    fitness++;
                }
            }
        }
        return fitness;
    }
}
