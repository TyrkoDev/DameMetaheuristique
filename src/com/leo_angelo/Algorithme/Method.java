package com.leo_angelo.Algorithme;

/**
 * Created by Angelo on 15/03/2017.
 */
public abstract class Method {
    protected Plateau plateau; // X0
    protected int fitness;     // f0
    protected int[][] listNeighbour;
    protected int numberNeighbour;
    protected int iterationNumber = 10;

    public Method(Plateau p) {
        this.plateau = p;
        this.initialisation();
    }

    public void initialisation() {
        this.fitness = this.calculateFitness(this.plateau.getChessBoard());
        System.out.println("Fitness du plateau initial : " + this.fitness);
        this.numberNeighbour = getNumberNeighbour();
    }

    public abstract void resolve();

    public abstract void chooseNeighbour();

    public int calculateFitness(int[] colomns) {
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

    public int getNumberNeighbour() {
        return (this.plateau.getChessBoard().length-1) * (this.plateau.getChessBoard().length/2);
    }

    public void getNeighbours() {
        this.listNeighbour = new int[this.numberNeighbour][];
        int count = 0;
        int columnTemp = 0;
        for(int i = 0; i < this.plateau.getChessBoard().length; i++) {
            for(int j = i+1; j < this.plateau.getChessBoard().length; j++) {
                this.listNeighbour[count] =  this.plateau.getChessBoard();
                columnTemp = this.listNeighbour[count][i];
                this.listNeighbour[count][i] = this.listNeighbour[count][j];

                this.listNeighbour[count][j] = columnTemp;

                count++;
            }
        }
    }

    public void displayPlateau() {
        for(int i = 0; i < listNeighbour.length; i++) {
            System.out.println("Voisins " + i + " : [");

            for(int j = 0; j < listNeighbour[i].length; j++) System.out.println(listNeighbour[i][j] + " ");

            System.out.println("]\n\n");
        }
    }

}
