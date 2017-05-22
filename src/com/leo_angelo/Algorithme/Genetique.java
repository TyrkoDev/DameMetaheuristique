package com.leo_angelo.Algorithme;

import com.leo_angelo.Vue.GenetiqueView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Angelo on 12/05/2017.
 */
public class Genetique extends Algorithm {
    private final static int NB_PLATEAU = 50;
    private final static int CHANGE_SAME_FITNESS = 50;
    private final static int SELECTION_PERCENTAGE = 10;

    private GenetiqueView genetiqueView;
    private int dimension;
    private int numberGeneration = 1700;
    private Map<Plateau, Double> parentGeneration;

    public Genetique() {
        genetiqueView = new GenetiqueView(this);
        genetiqueView.pack();
        genetiqueView.setVisible(true);
    }

    @Override
    public int getFitness(int[] columns) {
        int fitness = 0;

        for(int i = 0; i < columns.length; i++) {
            for(int j = i+1; j < columns.length; j++) {
                if ((Math.abs(columns[i] - columns[j]) == (Math.abs(i - j))) || (columns[i] == columns[j])) {
                    fitness++;
                }
            }
        }
        return fitness;
    }

    @Override
    public void initialisation() {
        parentGeneration = new HashMap<>();
        for(int i = 0; i < NB_PLATEAU; i++) {
            parentGeneration.put(new Plateau(dimension), 0.0);
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
    public Plateau reproduction(Map<Plateau, Double> listPlateau) {
        double max = 0.0;
        double proba;
        Plateau plateauReturn = null;

        for(Plateau plateau : listPlateau.keySet()) {
            proba = listPlateau.get(plateau);
            if(max <= proba) {
                max = proba;
                plateauReturn = plateau;
            }
        }
        return plateauReturn;
    }

    public Plateau reproductionWithoutPlateau(Map<Plateau, Double> listPlateau, Plateau plateauToRemove) {
        Map<Plateau, Double> newListPlateau = new HashMap<>();

        for(Plateau plateau : listPlateau.keySet()) {
            if(plateau != plateauToRemove) newListPlateau.put(plateau, listPlateau.get(plateau));
        }
        return reproduction(newListPlateau);
    }

    @Override
    public void resolve() {
        initialisation();
        Map<Plateau, Double> newGeneration;

        int fitness = getFitnessMinGeneration(parentGeneration);
        int fitnessCompare = fitness;
        int countSameFitness = 0;
        int count = 0;
        int percentageSelection;
        boolean exit = false;
        System.out.println("Plateau avec la fitness min départ : " + fitness);

        startTimer();
        while(!exit) {
            computeEachPlateauProbability(parentGeneration);

            newGeneration = new HashMap<>();
            Map tmpGeneration = new HashMap<>(parentGeneration);

            if(countSameFitness > CHANGE_SAME_FITNESS) {
                percentageSelection = SELECTION_PERCENTAGE * 2;
                countSameFitness = 0;
            } else percentageSelection =  SELECTION_PERCENTAGE;

            //-------------SELECTION
            while (newGeneration.size() <= (parentGeneration.size() / percentageSelection)) {
                Plateau son = reproduction(tmpGeneration);
                newGeneration.put(son, 0.0);
                tmpGeneration.remove(son);
            }

            //------------Crossing
            for (int k = 0; k < newGeneration.size()-1; k++) {
                Plateau mother = (Plateau) newGeneration.keySet().toArray()[k];
                Plateau father = (Plateau) newGeneration.keySet().toArray()[k + 1];
                Plateau son = crossing(mother, father);
                newGeneration.put(son, 0.0);

                k = k + 1;
            }

            //MUTATION
            while(newGeneration.size() < NB_PLATEAU) {
                Plateau son = reproduction(parentGeneration);
                if(Math.random() > 0.9) {
                    son = mutation(son);
                }
                newGeneration.put(son, 0.0);
                parentGeneration.remove(son);
            }

            parentGeneration = new HashMap<>(newGeneration);
            fitness = getFitnessMinGeneration(parentGeneration);

            if(fitnessCompare == fitness) countSameFitness++;

            genetiqueView.updateChart(fitness);

            count++;

            if(numberGeneration == count || fitness == 0) {
                exit = true;
            }
        }
        System.out.println("Time : " + endTimer());
        fitness = getFitnessMinGeneration(parentGeneration);
        System.out.println("Plateau avec la fitness min à la fin : " + fitness);
    }

    public void computeEachPlateauProbability(Map<Plateau, Double> listPlateau) {
        double proba;
        double fitness;
        double fitnessCumul = getFitnessCumul(listPlateau);

        for(Plateau plateau : listPlateau.keySet()) {
            fitness = getFitness(plateau.getChessBoard());
            proba = ((1/fitness) / fitnessCumul) * listPlateau.size();

            listPlateau.replace(plateau, proba);
        }
    }

    public int getFitnessCumul(Map<Plateau, Double> listPlateau) {
        int fitnessCumul = 0;
        for(Plateau plateau : listPlateau.keySet()) fitnessCumul = getFitness(plateau.getChessBoard());
        return fitnessCumul;
    }

    public int getFitnessMinGeneration(Map<Plateau, Double> listPlateau) {
        int fitnessMin = 1000000;
        int fitness;
        Plateau plateauFitnessMin = null;

        for(Plateau plateau : listPlateau.keySet()) {
            fitness = getFitness(plateau.getChessBoard());

            if(fitness < fitnessMin) {
                plateauFitnessMin = plateau;
                fitnessMin = fitness;
            }
        }

        if(fitnessMin == 0) {
            System.out.println(plateauFitnessMin);
        }


        return getFitness(plateauFitnessMin.getChessBoard());
    }
}
