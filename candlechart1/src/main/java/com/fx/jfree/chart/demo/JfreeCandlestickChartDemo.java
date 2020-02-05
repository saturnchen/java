package com.fx.jfree.chart.demo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.fx.jfree.chart.candlestick.JfreeCandlestickChart;
import com.fx.jfree.chart.common.FxMarketPxFeeder;

/**
 * The Class JfreeCandlestickChartDemo.
 * 
 * @author ashraf
 */
@SuppressWarnings("serial")
public class JfreeCandlestickChartDemo extends JPanel {

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("JfreeCandlestickChartDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the chart.
        JfreeCandlestickChart jfreeCandlestickChart = new JfreeCandlestickChart("TWTR");
        new FxMarketPxFeeder(jfreeCandlestickChart, "/twtr.csv", 2).run();
        frame.setContentPane(jfreeCandlestickChart);

        //Disable the resizing feature
        frame.setResizable(false);
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
