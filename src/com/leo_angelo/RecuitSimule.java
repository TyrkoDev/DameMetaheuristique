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
        calculFitness();
    }
}
