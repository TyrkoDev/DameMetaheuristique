package com.leo_angelo;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class PlateauGraph extends JDialog {
    private JPanel contentPane;
    private JPanel grille;
    private Plateau plateau;

    public PlateauGraph() {
        setContentPane(contentPane);
        setModal(true);

        this.plateau = new Plateau(10);
        System.out.print(plateau);
        RecuitSimule rs = new RecuitSimule(plateau);
        System.out.println("Fitness : " + rs.calculFitness(plateau.getEchiquierNumber()));

        Colomn[] matrice = this.plateau.getEchiquier();
        this.grille.setLayout(new GridLayout(10, 10));
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                JButton val = new JButton();
                val.setBackground(Color.WHITE);
                val.setText(matrice[i].getNum() == j ? "D" : " ");
                grille.add(val);
            }
        }
    }

    public static void main(String[] args) {
        PlateauGraph dialog = new PlateauGraph();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
