package com.leo_angelo.Vue;

import com.leo_angelo.Algorithme.Plateau;
import com.leo_angelo.Algorithme.Tabou;

import javax.swing.*;
import java.awt.*;

public class PlateauGraph extends JDialog {
    private JPanel contentPane;
    private JPanel grille;

    public PlateauGraph(Plateau plateau) {
        setContentPane(contentPane);
        setModal(true);
        int size = plateau.getSize();

        int[] matrice = plateau.getEchiquier();
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
        Plateau plateau = new Plateau(10);
        System.out.print(plateau);
        //RecuitSimule rs = new RecuitSimule(plateau);
        Tabou t = new Tabou(plateau);
        PlateauGraph dialog = new PlateauGraph(plateau);
        dialog.pack();
        dialog.setVisible(true);
    }
}
