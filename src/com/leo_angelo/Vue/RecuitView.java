package com.leo_angelo.Vue;

import com.leo_angelo.Algorithme.Plateau;
import com.leo_angelo.Algorithme.RecuitSimule;
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

public class RecuitView extends JDialog {
    private JPanel contentPane;
    private JTextField sizePlateauField;
    private JTextField probabilityField;
    private JButton startButton;
    private JTextField variationTemperatureField;
    private JPanel params;
    private JPanel graphPanel;
    private JPanel plateauPanel;
    private XYSeries series;
    private int variationTemperature = 0;
    private Plateau plateau;
    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public RecuitView(Plateau plateau, RecuitSimule recuitSimule) {
        setContentPane(contentPane);
        setModal(true);
        this.sizePlateauField.setText("10");
        this.probabilityField.setText("0.5");
        this.variationTemperatureField.setText("0.99");
        this.plateau = plateau;
        this.dataset = new XYSeriesCollection();

        initGraph();

        chart.getPlot().addChangeListener(new PlotChangeListener() {
            @Override
            public void plotChanged(PlotChangeEvent event) {
                chart.fireChartChanged();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plateau.changeSize(Integer.parseInt(sizePlateauField.getText()));
                dataset.removeAllSeries();
                variationTemperature = 0;
                initGraph();
                recuitSimule.setParam(Double.valueOf(probabilityField.getText()), Double.valueOf(variationTemperatureField.getText()), plateau);

                new Thread() {
                    public void run() {
                        try {
                            recuitSimule.resolve();
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
                "Recuit simulé", // Title
                "Variation de température", // x-axis Label
                "Fitness", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                false, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );

        this.chartPanel = new ChartPanel(chart);
        graphPanel.setLayout(new java.awt.BorderLayout());
        graphPanel.add(chartPanel, BorderLayout.CENTER);
        graphPanel.validate();
    }

    public void drawResult(Plateau p) {
        System.out.println("result");
        this.plateauPanel.removeAll();
        this.plateauPanel.validate();
        int size = p.getSize();

        this.series = new XYSeries("data");

        int[] matrice = p.getChessBoard();
        this.plateauPanel.setLayout(new GridLayout(size, size));
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                JButton valueButton = new JButton();
                valueButton.setBackground(Color.WHITE);
                valueButton.setText(matrice[i] == j ? "D" : " ");
                plateauPanel.add(valueButton);
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
    }
}
