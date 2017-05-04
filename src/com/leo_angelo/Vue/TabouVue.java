package com.leo_angelo.Vue;

import com.leo_angelo.Algorithme.Plateau;
import com.leo_angelo.Algorithme.Tabou;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TabouVue extends JDialog {
    private JPanel contentPane;
    private JButton lancer;
    private JTextField dimensionField;
    private JTextField tailleField;
    private JPanel plateauPanel;
    private JPanel params;
    private JPanel graphPanel;
    private Plateau plateau;
    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel cp;
    private XYSeries series;
    private int iteration = 0;

    public TabouVue(Plateau p, Tabou tabou) {
        setContentPane(contentPane);
        setModal(true);
        this.dimensionField.setText("10");
        this.tailleField.setText("2");
        this.plateau = p;
        this.dataset = new XYSeriesCollection();

        initGraph();

        chart.getPlot().addChangeListener(new PlotChangeListener() {
            @Override
            public void plotChanged(PlotChangeEvent event) {
                chart.fireChartChanged();
            }
        });

        lancer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plateau.changeSize(Integer.parseInt(dimensionField.getText()));
                dataset.removeAllSeries();

                initGraph();

                iteration = 0;
                tabou.setParam(Integer.parseInt(tailleField.getText()), plateau, 100);

                new Thread() {
                    public void run() {
                        try {
                            tabou.resolve();
                        } catch(Exception v) {
                            System.out.println(v);
                        }
                    }
                }.start();
            }
        });

    }

    private void initGraph() {
        graphPanel.removeAll();
        this.series = new XYSeries("values");
        // Generate the graph
        this.chart = ChartFactory.createXYLineChart(
                "Tabou", // Title
                "Itération", // x-axis Label
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


    public void updateChart(int fitness) {
        series.add(iteration, fitness);
        iteration++;

        // Add the series to your data set
        dataset.removeAllSeries();
        dataset.addSeries(series);
    }
}
