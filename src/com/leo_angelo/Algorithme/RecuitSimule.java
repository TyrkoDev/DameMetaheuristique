package com.leo_angelo.Algorithme;

import com.leo_angelo.Vue.PlateauGraph;
import com.leo_angelo.Vue.RecuitVue;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

/**
 * Created by Angelo on 15/03/2017.
 */
public class RecuitSimule extends Methode {

    private int choix = -1;
    private int deltaf = -1;
    private int fitnessMin = -1;
    private double temperature;
    private Plateau voisin;
    private Plateau meilleureSolution;
    private RecuitVue recuitVue;
    private double p = 0.5;
    private double u = 0.99;

    public RecuitSimule(Plateau p) {
        super(p);

        recuitVue = new RecuitVue(this.plateau, this);
        recuitVue.pack();
        recuitVue.setVisible(true);
    }

    @Override
    public void init() {
        super.init();
        choix = -1;
        deltaf = -1;
        fitnessMin = -1;
    }

    public int calculDelta(int fitnessVoisin) {
        return fitnessVoisin - this.calculFitness(this.plateau.getEchiquier());
    }

    public void setParam(double p, double u, Plateau plateau) {
        this.p = p;
        this.u = u;
        this.plateau = plateau;
    }

    public double getTemperature(double delta, double p) {
        return delta / Math.log(p);
    }

    public void resolve() {
        init();
        boolean sortie = false;
        int fitnessVoisin;
        temperature = getTemperature(deltaf, p);
        meilleureSolution = plateau; // Initialisation de Xmin
        fitnessMin = calculFitness(meilleureSolution.getEchiquier()); // Initialisation de F(Xmin)

        while(!sortie) {
            for (int i = 0; i < 5; i++) {
                getVoisins();//On liste les voisins de la solution actuelle
                choisirVoisin();
                voisin = new Plateau(this.listeVoisins[choix]);//On crée l'objet voisin
                fitnessVoisin = calculFitness(voisin.getEchiquier());
                deltaf = calculDelta(fitnessVoisin);//On calcule le delta (différence de fitness entre la sol actuelle et la sol voisin
                //System.out.println(voisin);//On affiche la solution voisin

                if (deltaf <= 0) { // si delta inf ou egal à 0
                    plateau = voisin; // Le voisin devient Xi+1
                    if (fitnessVoisin < fitnessMin) { // Si une meilleure fitness --> on affecte comme meilleure solution
                        fitnessMin = fitnessVoisin;
                        meilleureSolution = voisin;
                    }
                } else { //Sinon
                    if (Math.random() <= Math.exp(-deltaf / temperature)) plateau = voisin; // Si condition == true alors voisin devient Xi+1
                    //Sinon on garde le plateau actuel
                }
            }

            recuitVue.updateChart(calculFitness(plateau.getEchiquier())); //On notifie la mise à jour du plateau

            temperature *= u; // on décroit la temperature jusqu'à 0.000001
            if(temperature < 0.0000000000000001 || fitnessMin == 0) sortie = true; //Condition de sortie
        }
    }

    @Override
    public void choisirVoisin() {
        choix = (int) (Math.random() * (this.listeVoisins.length));//On en choisi 1 aléatoirement
    }
}