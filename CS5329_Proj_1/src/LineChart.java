/*******************************************************************************************************
 * This JFreeChart Line Chart Code is originally from tutorialspoint.com, but I have modified it slightly
 * Url: https://www.tutorialspoint.com/jfreechart/jfreechart_line_chart.htm
 * Accessed: 1/25/21
 *******************************************************************************************************/

import java.awt.Color;
import java.awt.BasicStroke; 

import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

public class LineChart extends ApplicationFrame {
   ChartPanel chartPanel;

   public LineChart(String applicationTitle, String chartTitle, String xAxisName, String yAxisName, XYDataset xyDataset ) {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle,
         xAxisName,
         yAxisName,
         xyDataset,
         PlotOrientation.VERTICAL,
         true , true , false);
         
      chartPanel = new ChartPanel(xylineChart);
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ));
      XYPlot plot = xylineChart.getXYPlot();
      
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesPaint( 2 , Color.YELLOW );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
      renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel );

      // LineChart chart = this;
      // chart.pack( );
      // RefineryUtilities.centerFrameOnScreen( chart );
      // chart.setVisible( true );
   }

   public ChartPanel getChartPanel() {
      return chartPanel;
   }
}