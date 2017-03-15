package com.leo_angelo;

import java.util.ArrayList;

/**
 * Created by Angelo on 15/03/2017.
 */
public class Plateau {
    private ArrayList<Colomn> colomns = new ArrayList<>();
    private Colomn[] echiquier;

    private int size = 0;

    public Plateau(int n) {
        this.size = n;

        for(int i = 0; i < this.size; i++) {
            colomns.add(new Colomn(i));
        }

        this.echiquier = new Colomn[n];
        for(int i = 0; i < this.size; i++) {
            int choix = (int) (Math.random() * (this.colomns.size()));
            this.echiquier[i] = this.colomns.get(choix);
            this.colomns.remove(choix);
        }
    }

    public Colomn[] getEchiquier() {
        return this.echiquier;
    }

    public String toString() {
        StringBuilder stb = new StringBuilder();
        for(int i = 0; i < this.size; i++) {
            for(int j = 0; j < this.size; j++) {
                stb.append((echiquier[i].getNum() == j ? 1 : 0) + " ");
            }
            stb.append("\n");
        }

        return stb.toString();
    }
}
