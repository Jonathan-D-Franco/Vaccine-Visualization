// these are the charts that appear when the visualize button is pressed


import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
 
public class gui extends JFrame {
   private static JFreeChart chart;
   private static DatabaseTool datum;
   private gui chartPoint = this;
   
public gui(String title, int choice, DatabaseTool data) {
      super( title ); 
      datum = data;
      setContentPane(createDemoPanel(choice));
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   }
   
   public PieDataset createPDataset(Hashtable country ) {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      Enumeration countries = country.keys();
      while(countries.hasMoreElements())
      {
         String countryName = (String) countries.nextElement();
         dataset.setValue(countryName, (int) country.get(countryName));
      }  
      return dataset;         
   }
   
   private static JFreeChart createBChart(CategoryDataset data)
   {
      JFreeChart barChart = ChartFactory.createBarChart(
         "Vaccination Manufacturer Distribution",           
         "Manufacturers",            
         "Vaccinations",            
         data,          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      return barChart; 
   }

   public CategoryDataset createBDataset(double sumPfizer, double sumModerna, double sumJohnson, double sumNova, double sumSino, double sumAstra, double sumOther) {
      final String pfizer = "Pfizer";        
      final String mod = "Moderna";        
      final String jnj = "Johnson&Johnson";        
      final String nova = "Novavax";        
      final String sino = "Sinovac";        
      final String astra = "AstraZeneca";
      final String other = "Unknown Manufacturer";   
      final String fill = " ";         
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( );  

      dataset.addValue( sumPfizer , pfizer, fill);        
      dataset.addValue( sumModerna , mod , fill );        
      dataset.addValue( sumJohnson , jnj , fill ); 
      dataset.addValue( sumNova , nova , fill );           

      dataset.addValue( sumSino , sino , fill );        
      dataset.addValue( sumAstra , astra , fill ); 
      dataset.addValue( sumOther, other, fill);                   

      return dataset; 
   }

   private static JFreeChart createPChart( PieDataset dataset ) {
      JFreeChart chart = ChartFactory.createPieChart(      
         "Vaccine Distribution",   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);

      return chart;
   }
   
   public JPanel createDemoPanel(int choice) {
      if (choice == 0)
         chart = createBChart(datum.sendBar(chartPoint) );  
      if (choice == 1)
         chart = createPChart(datum.sendPie(chartPoint) );  
      return new ChartPanel( chart ); 
   }
}

