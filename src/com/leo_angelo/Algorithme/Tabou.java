package com.leo_angelo.Algorithme;

import com.leo_angelo.Vue.TabouView;

/**
 * Created by Angelo on 15/03/2017.
 */
public class Tabou extends AlgorithmWithNeighbours {

    private int[] fitnessNeighbours;
    private int[] listTabou;

    private int indexTabou = 0;
    private int size = 3;
    private int iBestNeighbour;

    private TabouView tabouView;

    public Tabou(Plateau p) {
        super(p);
        initialisation();

        tabouView = new TabouView(this.plateau, this);
        tabouView.pack();
        tabouView.setVisible(true);
    }

    @Override
    public void initialisation() {
        super.initialisation();
        this.listNeighbour = new int[this.numberNeighbour][];
        this.fitnessNeighbours = new int[this.listNeighbour.length];
        this.listTabou = new int[size];
    }

    public void setParam(int tailleListe, Plateau p, int nbIteration) {
        this.plateau = p;
        this.size = tailleListe;
        this.iterationNumber = nbIteration;
    }

    public void resolve() {
        initialisation();
        int i = 0;
        while(i< iterationNumber && fitness != 0) {
            i++;
            tabouView.updateChart(fitness);
            chooseNeighbour();
        }
        tabouView.updateChart(fitness);
    }


    @Override
    public void chooseNeighbour() {
        getNeighbours();
        //this.fitnessNeighbours = new int[this.listNeighbour.length];
        /*for(int i = 0; i<this.listNeighbour.length; i++)
            this.fitnessNeighbours[i] = getFitness(this.listNeighbour[i]);*/

        //int iBestNeighbour = findMaximumFitness();

        if(this.fitnessNeighbours[iBestNeighbour] >= this.fitness) {
            addTabou(iBestNeighbour);
        }

        this.plateau = new Plateau(listNeighbour[iBestNeighbour]);
        this.fitness = this.fitnessNeighbours[iBestNeighbour];
    }

    public int findMaximumFitness() {
        int imax = 0;
        for(int i = 1; i<this.fitnessNeighbours.length; i++) {
            if(this.fitnessNeighbours[i] < this.fitnessNeighbours[imax] && !isTabou(i)) {
                imax = i;
            }
        }
        return imax;
    }

    public boolean isTabou(int neighbourToTest) {
        if(listTabou != null) {
            for (int i = 0; i< listTabou.length; i++) {
                if(listTabou[i] == neighbourToTest) return true;
            }
        }
        return false;
    }

    public void addTabou(int neighbourTabou) {
        listTabou[this.indexTabou] = neighbourTabou;
        if(this.indexTabou >= size - 1) this.indexTabou = 0;
        else this.indexTabou++;
    }

    public void getNeighbours() {
        this.iBestNeighbour = 0;
        int count = 0;
        int columnTemp = 0;
        for(int i = 0; i < this.plateau.getChessBoard().length; i++) {
            for(int j = i+1; j < this.plateau.getChessBoard().length; j++) {
                this.listNeighbour[count] =  this.plateau.getChessBoard();
                columnTemp = this.listNeighbour[count][i];
                this.listNeighbour[count][i] = this.listNeighbour[count][j];

                this.listNeighbour[count][j] = columnTemp;
                this.fitnessNeighbours[count] = getFitness(this.listNeighbour[count], 0);
                if(this.fitnessNeighbours[count] < this.fitnessNeighbours[iBestNeighbour] && !isTabou(count)) iBestNeighbour = count;
                count++;
            }
        }
    }
}