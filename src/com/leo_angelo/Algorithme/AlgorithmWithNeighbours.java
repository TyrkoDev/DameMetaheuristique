package com.leo_angelo.Algorithme;

/**
 * Created by Angelo on 15/03/2017.
 */
public abstract class AlgorithmWithNeighbours extends Algorithm {
    protected int[][] listNeighbour;
    protected int numberNeighbour;
    protected Plateau plateau; // X0

    public AlgorithmWithNeighbours(Plateau p) {
        this.plateau = p;
        this.initialisation();
    }

    public void initialisation() {
        this.fitness = this.getFitness(this.plateau.getChessBoard());
        this.numberNeighbour = getNumberNeighbour();
    }

    public abstract void chooseNeighbour();

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
}
