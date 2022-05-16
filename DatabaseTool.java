// used to calculate the data that is then sent to the charts to be visualized

import java.util.Dictionary;
import java.util.Hashtable;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.RefineryUtilities;

public class DatabaseTool {
	private int sumPfizer = 0;
	private int sumModerna = 0;
	private int sumJohnson = 0;
	private int sumNova = 0;
	private int sumSino = 0;
	private int sumAstra = 0;
	private int sumOther = 0;
	private DatabaseTool data = this;
	Hashtable country = new Hashtable<String, Integer>();
	public DatabaseTool() {
		
	}
	
	public void sumVaccType(Dictionary dict) {
		 sumPfizer = 0;
		 sumModerna = 0;
		 sumJohnson = 0;
		 sumNova = 0;
		 sumSino = 0;
		 sumAstra = 0;
		 sumOther = 0;
		for (int i = 1; i < dict.size(); i++) {
			if (((String []) dict.get(i))[3].equals("Pfizer")) {
				sumPfizer++;
			} else if (((String []) dict.get(i))[3].equals("Moderna")) {
				sumModerna++;
			} else if (((String []) dict.get(i))[3].equals("Johnson&Johnson")) {
				sumJohnson++;
			} else if (((String []) dict.get(i))[3].equals("Novavax")) {
				sumNova++;
			} else if (((String []) dict.get(i))[3].equals("Sinovac")) {
				sumSino++;
			} else if (((String []) dict.get(i))[3].equals("AstraZeneca")) {
				sumAstra++;
			} else {
				sumOther++;
			}
		}
		
		//CALL OUTPUT GUI METHODS
		
		gui demoBar = new gui( "Vaccine Location", 0, data);     
		demoBar.setSize( 560 , 367 );    
		RefineryUtilities.centerFrameOnScreen( demoBar );  
		demoBar.setVisible( true );
		
		}
	
	public void sumVaccLocation(Dictionary dict) {
		
		for (int i = 1; i < dict.size(); i++) {
			if (country.containsKey(((String[]) dict.get(i))[5])) {
				country.put(((String[]) dict.get(i))[5], ((int) country.get(((String[]) dict.get(i))[5])) + 1);
			} else {
				country.put(((String[]) dict.get(i))[5], 1);
			}
		}
		
		//CALL OUTPUT GUI METHODS
		gui demoPie = new gui( "Vaccine Distribution", 1, data); 
		demoPie.setSize( 560 , 367 ); 
		RefineryUtilities.centerFrameOnScreen( demoPie ); 
		demoPie.setVisible( true );
	}
	public CategoryDataset sendBar(gui chartPoint)
	{
		return chartPoint.createBDataset((double) sumPfizer, (double) sumModerna, (double) sumJohnson, (double) sumNova, (double) sumSino, (double) sumAstra, (double) sumOther);

	}

	public PieDataset sendPie(gui chartPoint)
	{
		return chartPoint.createPDataset(country);
	}
		
	}
