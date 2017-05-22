package com.leo_angelo.Algorithme;

/**
 * Created by Angelo on 21/05/2017.
 */
public abstract class Algorithm {
    protected int fitness;     // f0
    protected int iterationNumber = 10;
    protected long timeStart;

    public abstract void initialisation();

    public abstract void resolve();

    public int getFitness(int[] columns) {
        int fitness = 0;

        for(int i = 0; i < columns.length; i++) {
            for(int j = i+1; j < columns.length; j++) {
                if (Math.abs(columns[i] - columns[j]) == (Math.abs(i - j))) {
                    fitness++;
                }
            }
        }
        return fitness;
    }

    public void startTimer() {
        timeStart = System.currentTimeMillis();

    }

    public float endTimer() {
        long end = System.currentTimeMillis();
        return ((float) (end - timeStart)) / 1000f;
    }

    public int getFitness(int[] colomns, int index) {
        int fitness = 0;
        if (index < colomns.length) {
            for (int i = index + 1; i < colomns.length; i++) {
                if (Math.abs(colomns[index] - colomns[i]) == i - index) {
                    fitness++;
                }
            }
            index++;
            fitness += getFitness(colomns, index);
        }
        return fitness;
    }
}
