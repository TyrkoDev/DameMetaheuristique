package com.leo_angelo.Vue;

import com.leo_angelo.Algorithme.Genetique;
import com.leo_angelo.Algorithme.Plateau;
import com.leo_angelo.Algorithme.RecuitSimule;
import com.leo_angelo.Algorithme.Tabou;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JDialog {
    private JPanel contentPane;
    private JPanel top;
    private JPanel choicePanel;
    private JButton genetiqueButton;
    private JButton tabouButton;
    private JButton recuitButton;
    private JPanel label;
    private Plateau plateau;

    public Menu() {
        setContentPane(contentPane);
        setModal(true);

        //Colors
        this.contentPane.setBackground(Color.darkGray);
        this.top.setBackground(Color.darkGray);
        this.choicePanel.setBackground(Color.darkGray);
        this.genetiqueButton.setBackground(Color.darkGray);
        this.tabouButton.setBackground(Color.darkGray);
        this.recuitButton.setBackground(Color.darkGray);
        this.label.setBackground(Color.darkGray);

        //Retouche des bouttons
        tabouButton.setBorder(null);
        tabouButton.setBorderPainted(false);
        recuitButton.setBorder(null);
        recuitButton.setBorderPainted(false);
        genetiqueButton.setBorder(null);
        genetiqueButton.setBorderPainted(false);

        tabouButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialisation();
                new Tabou(plateau);
            }
        });

        recuitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialisation();
                new RecuitSimule(plateau);
            }
        });

        genetiqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initialisation();
                new Genetique(plateau).resolve();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void initialisation() {
        plateau = new Plateau(10);
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        Menu dialog = new Menu();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
