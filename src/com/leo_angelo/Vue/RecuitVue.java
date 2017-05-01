package com.leo_angelo.Vue;

import com.leo_angelo.Algorithme.Plateau;
import com.leo_angelo.Algorithme.RecuitSimule;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.DoubleSummaryStatistics;

public class RecuitVue extends JDialog {
    private JPanel contentPane;
    private JTextField sizePlateau;
    private JTextField proba;
    private JButton lancerButton;
    private JTextField varTemp;
    private JPanel params;
    private JPanel graphPanel;
    private JPanel plateauPanel;
    private XYSeries series;
    private int variationTemperature = 0;
    private Plateau plateau;
    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel cp;

    public RecuitVue(Plateau plateau, RecuitSimule recuitSimule) {
        setContentPane(contentPane);
        setModal(true);
        this.sizePlateau.setText("10");
        this.proba.setText("0.5");
        this.varTemp.setText("0.99");
        this.plateau = plateau;
        this.dataset = new XYSeriesCollection();

        initGraph();
        //drawResult(plateau);

        chart.getPlot().addChangeListener(new PlotChangeListener() {
            @Override
            public void plotChanged(PlotChangeEvent event) {
                chart.fireChartChanged();
                contentPane.repaint();
                contentPane.revalidate();
            }
        });

        lancerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plateau.changeSize(Integer.parseInt(sizePlateau.getText()));
                dataset.removeAllSeries();
                variationTemperature = 0;
                initGraph();
                recuitSimule.setParam(Double.valueOf(proba.getText()), Double.valueOf(varTemp.getText()), plateau);
                recuitSimule.resolve();
            }
        });
    }

    private void initGraph() {
        graphPanel.removeAll();
        this.series = new XYSeries("values");
        // Generate the graph
        this.chart = ChartFactory.createXYLineChart(
                "Recuit simulé", // Title
                "Variation de température", // x-axis Label
                "Fitness", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                false, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        this.cp = new ChartPanel(chart);
        graphPanel.setLayout(new java.awt.BorderLayout());
        graphPanel.add(cp, BorderLayout.CENTER);
        graphPanel.validate();
    }

    public void drawResult(Plateau p) {
        System.out.println("result");
        this.plateauPanel.removeAll();
        this.plateauPanel.validate();
        int size = p.getSize();

        this.series = new XYSeries("data");

        int[] matrice = p.getEchiquier();
        this.plateauPanel.setLayout(new GridLayout(size, size));
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                JButton val = new JButton();
                val.setBackground(Color.WHITE);
                val.setText(matrice[i] == j ? "D" : " ");
                plateauPanel.add(val);
            }
        }
        this.plateauPanel.repaint();
        this.plateauPanel.validate();
    }

    public void updateChart(int fitness) {
        series.add(variationTemperature, fitness);
        variationTemperature++;

        // Add the series to your data set
        dataset.removeAllSeries();
        dataset.addSeries(series);

        /*this.cp = new ChartPanel(chart);
        graphPanel.setLayout(new java.awt.BorderLayout());
        graphPanel.add(cp, BorderLayout.CENTER);
        graphPanel.validate();
        graphPanel.repaint();*/

    }
}
