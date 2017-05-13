package com.leo_angelo.Algorithme;

import com.leo_angelo.Vue.RecuitView;

/**
 * Created by Angelo on 15/03/2017.
 */
public class RecuitSimule extends Method {

    private int choice = -1;
    private int deltaf = -1;
    private int fitnessMin = -1;
    private double temperature;
    private Plateau neighbourPlateau;
    private Plateau bestSolution;
    private RecuitView recuitView;
    private double probability = 0.5;
    private double u = 0.99;

    public RecuitSimule(Plateau p) {
        super(p);

        recuitView = new RecuitView(this.plateau, this);
        recuitView.pack();
        recuitView.setVisible(true);
    }

    @Override
    public void initialisation() {
        super.initialisation();
        choice = -1;
        deltaf = -1;
        fitnessMin = -1;
    }

    public int calculDelta(int fitnessVoisin) {
        return fitnessVoisin - this.calculateFitness(this.plateau.getChessBoard());
    }

    public void setParam(double p, double u, Plateau plateau) {
        this.probability = p;
        this.u = u;
        this.plateau = plateau;
    }

    public double getTemperature(double delta, double p) {
        return delta / Math.log(p);
    }

    public void resolve() {
        initialisation();
        boolean sortie = false;
        int fitnessVoisin;
        temperature = getTemperature(deltaf, probability);
        bestSolution = plateau; // Initialisation de Xmin
        fitnessMin = calculateFitness(bestSolution.getChessBoard()); // Initialisation de F(Xmin)

        while(!sortie) {
            for (int i = 0; i < 5; i++) {
                getNeighbours();//On liste les voisins de la solution actuelle
                chooseNeighbour();
                neighbourPlateau = new Plateau(this.listNeighbour[choice]);//On crée l'objet neighbourPlateau
                fitnessVoisin = calculateFitness(neighbourPlateau.getChessBoard());
                deltaf = calculDelta(fitnessVoisin);//On calcule le delta (différence de fitness entre la sol actuelle et la sol neighbourPlateau
                //System.out.println(neighbourPlateau);//On affiche la solution neighbourPlateau

                if (deltaf <= 0) { // si delta inf ou egal à 0
                    plateau = neighbourPlateau; // Le neighbourPlateau devient Xi+1
                    if (fitnessVoisin < fitnessMin) { // Si une meilleure fitness --> on affecte comme meilleure solution
                        fitnessMin = fitnessVoisin;
                        bestSolution = neighbourPlateau;
                    }
                } else { //Sinon
                    if (Math.random() <= Math.exp(-deltaf / temperature)) plateau = neighbourPlateau; // Si condition == true alors neighbourPlateau devient Xi+1
                    //Sinon on garde le plateau actuel
                }
            }

            recuitView.updateChart(calculateFitness(plateau.getChessBoard())); //On notifie la mise à jour du plateau

            temperature *= u; // on décroit la temperature jusqu'à 0.000001
            if(temperature < 0.0000000000000001 || fitnessMin == 0) sortie = true; //Condition de sortie
        }
    }

    @Override
    public void chooseNeighbour() {
        choice = (int) (Math.random() * (this.listNeighbour.length));//On en choisi 1 aléatoirement
    }
}