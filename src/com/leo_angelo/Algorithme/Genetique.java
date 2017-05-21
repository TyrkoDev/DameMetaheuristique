package com.leo_angelo.Algorithme;

import com.leo_angelo.Vue.GenetiqueView;

import java.util.ArrayList;

/**
 * Created by Angelo on 12/05/2017.
 */
public class Genetique extends Algorithm {
    private final static int NB_PLATEAU = 50;

    private GenetiqueView genetiqueView;
    private int dimension;
    private int numberGeneration = 1700;
    private ArrayList<Plateau> listPlateau = new ArrayList<>();

    public Genetique() {
        genetiqueView = new GenetiqueView(this);
        genetiqueView.pack();
        genetiqueView.setVisible(true);
    }

    @Override
    public void initialisation() {
        listPlateau = new ArrayList<>();
        for(int i = 0; i < NB_PLATEAU; i++) {
            listPlateau.add(new Plateau(dimension));
        }
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getNumberGeneration() {
        return numberGeneration;
    }

    public void setNumberGeneration(int numberGeneration) {
        this.numberGeneration = numberGeneration;
    }

    /**
     * Altération aléatoire de quelques solutions
     *          Prendre la meilleure solution (meilleure fitness) et changé aléatoirement des positions
     *          de dames sur le plateau
     */
    public Plateau mutation(Plateau plateau) {
        int[] chessBoard = plateau.getChessBoard();
        int columnToSwap = (int) (Math.random() * dimension);
        int columnToSwapWith = columnToSwap;
        int columnTemp;

        while(columnToSwap == columnToSwapWith)
            columnToSwapWith = (int) (Math.random() * dimension);

        columnTemp = chessBoard[columnToSwap];
        chessBoard[columnToSwap] = chessBoard[columnToSwapWith];
        chessBoard[columnToSwapWith] = columnTemp;

        plateau.setChessBoard(chessBoard);

        return plateau;
    }

    /**
     * Recombinaison des solutions reproduites
     *          Intervertir les positions des dames d'un sous-ensemble de 2 solutions
     *          ex : 0011|01100 -->  0011|00011
     *               1001|00011 -->  1001|01100
     */
    public Plateau crossing(Plateau plateau1, Plateau plateau2) {
        int[] chessBoard1 = plateau1.getChessBoard();
        int[] chessBoard2 = plateau2.getChessBoard();
        int[] newChessBoard = new int[dimension];

        int cut = (int) (Math.random() * dimension);
        Plateau crossedPlateau = new Plateau(dimension);

        for(int i = 0; i < dimension; i++) {
            if(i < cut) newChessBoard[i] = chessBoard1[i];
            else newChessBoard[i] = chessBoard2[i];
        }

        crossedPlateau.setChessBoard(newChessBoard);

        return crossedPlateau;
    }

    /**
     * Séléction de certaines solutions
     *          Prendre aléatoirement une solution voisine
     */
    public Plateau reproduction(ArrayList<Plateau> listPlateau) {
        return listPlateau.get((int) (Math.random() * listPlateau.size()));
    }

    public Plateau reproductionWithoutPlateau(ArrayList<Plateau> listPlateau, Plateau plateau) {
        ArrayList<Plateau> newListPlateau = new ArrayList<>();
        Plateau plateauTemp;
        for(int i = 0; i < listPlateau.size(); i++) {
            plateauTemp = listPlateau.get(i);
            if(plateauTemp != plateau) newListPlateau.add(plateauTemp);
        }
        return reproduction(newListPlateau);
    }

    @Override
    public void resolve() {
        initialisation();
        ArrayList<Plateau> newListPlateau;
        Plateau plateauRandom1;
        Plateau plateauRandom2;
        Plateau plateauCrossing;

        int fitness = getPlateauFitnessMin(listPlateau);
        System.out.println("Plateau avec la fitness min départ : " + fitness);

        for(int i = 0; i < numberGeneration; i++) {
            newListPlateau = new ArrayList<>();
            for(int j = 0; j < NB_PLATEAU; j++) {
                plateauRandom1 = reproduction(listPlateau);
                plateauRandom2 = reproductionWithoutPlateau(listPlateau, plateauRandom1);
                plateauCrossing = crossing(plateauRandom1, plateauRandom2);

                if(Math.random() > 0.9) {
                    plateauCrossing = mutation(plateauCrossing);
                }
                if(getFitness(plateauCrossing.getChessBoard()) <= fitness)
                    newListPlateau.add(plateauCrossing);
            }

            if(newListPlateau.size() > 1) {
                listPlateau = newListPlateau;
                fitness = getPlateauFitnessMin(listPlateau);
                genetiqueView.updateChart(fitness);
            }
        }

        fitness = getPlateauFitnessMin(listPlateau);
        System.out.println("Plateau avec la fitness min à la fin : " + fitness);
    }

    public int getPlateauFitnessMin(ArrayList<Plateau> listPlateau) {
        int fitnessMin = 1000000;
        int fitness;
        Plateau plateauFitnessMin = null;

        for(Plateau plateau : listPlateau) {
            fitness = getFitness(plateau.getChessBoard());

            if(fitness < fitnessMin) {
                plateauFitnessMin = plateau;
                fitnessMin = fitness;
            }
        }

        return getFitness(plateauFitnessMin.getChessBoard());
    }
}
