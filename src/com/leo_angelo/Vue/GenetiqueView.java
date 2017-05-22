package com.leo_angelo.Vue;

import com.leo_angelo.Algorithme.Genetique;
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
import java.awt.event.*;

import static java.lang.Thread.sleep;

public class GenetiqueView extends JDialog {
    private JPanel contentPane;
    private JPanel graphic;
    private JPanel params;
    private JTextField inputDimension;
    private JTextField inputGeneration;
    private JButton start;
    private int generation = 0;
    private Genetique genetique;

    private XYSeriesCollection dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private XYSeries series;

    public GenetiqueView(Genetique genetique) {
        setContentPane(contentPane);
        setModal(true);

        this.genetique = genetique;
        this.dataset = new XYSeriesCollection();
        this.inputDimension.setText("8");
        this.inputGeneration.setText("100");

        initGraph();

        chart.getPlot().addChangeListener(new PlotChangeListener() {
            @Override
            public void plotChanged(PlotChangeEvent event) {
                chart.fireChartChanged();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genetique.setNumberGeneration(Integer.parseInt(inputGeneration.getText()));
                genetique.setDimension(Integer.parseInt(inputDimension.getText()));
                dataset.removeAllSeries();
                generation = 0;
                initGraph();

                new Thread() {
                    public void run() {
                        try {
                            genetique.resolve();
                        } catch(Exception v) {
                            System.out.println(v);
                        }
                    }
                }.start();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void initGraph() {
        graphic.removeAll();
        this.series = new XYSeries("values");

        this.chart = ChartFactory.createXYLineChart(
                "Génétique", // Title
                "Nombre de génération", // x-axis Label
                "Fitness", // y-axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL, // Plot Orientation
                false, // Show Legend
                true, // Use tooltips
                false // Configure chart to generate URLs?
        );
        this.chartPanel = new ChartPanel(chart);
        graphic.setLayout(new java.awt.BorderLayout());
        graphic.add(chartPanel, BorderLayout.CENTER);
        graphic.validate();
    }

    public void updateChart(int fitness) {
        series.add(generation, fitness);
        generation++;

        // Add the series to your data set
        dataset.removeAllSeries();
        dataset.addSeries(series);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
