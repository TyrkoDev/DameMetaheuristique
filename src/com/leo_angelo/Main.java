package com.leo_angelo;

public class Main {

    public static void main(String[] args) {
	    Plateau plateau = new Plateau(10);
        System.out.print(plateau);
        //RecuitSimule rs = new RecuitSimule(plateau);
        Tabou t = new Tabou(plateau);
        System.out.println("Fitness : " + t.calculFitness(plateau.getEchiquier()));
        t.choisirVoisin();
    }
}
