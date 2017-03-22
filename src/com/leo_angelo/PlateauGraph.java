package com.leo_angelo;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class PlateauGraph extends JDialog {
    private JPanel contentPane;
    private JPanel grille;
    private Plateau plateau;

    public PlateauGraph(int size) {
        setContentPane(contentPane);
        setModal(true);

        this.plateau = new Plateau(size);
        System.out.print(plateau);
        //RecuitSimule rs = new RecuitSimule(plateau);
        Tabou t = new Tabou(plateau);
        System.out.println("Fitness : " + t.calculFitness(plateau.getEchiquier()));

        int[] matrice = this.plateau.getEchiquier();
        this.grille.setLayout(new GridLayout(size, size));
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                JButton val = new JButton();
                val.setBackground(Color.WHITE);
                val.setText(matrice[i] == j ? "D" : " ");
                grille.add(val);
            }
        }
    }

    public static void main(String[] args) {
        PlateauGraph dialog = new PlateauGraph(10);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
