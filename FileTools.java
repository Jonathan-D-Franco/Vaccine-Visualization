//Reads the loaded file and writes into a new one for the save functionality

import java.io.*;
import java.util.Dictionary;

public class FileTools {
	
	public FileTools() {
		
	}
	
	public Dictionary ReadFile(String filepath, Frame frame, Dictionary base) {
		String delimiter = ",";
		String line = "";
		//Dictionary base = new Hashtable<Integer, String[]>();
		boolean notEmpty = false, match = false, valid = true;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath)); //Create buffered reader
			int i = 0;
			
			//Loop through file
			while ((line = br.readLine()) != null) {
				if( notEmpty == false)
					notEmpty = true;
				String [] row = line.split(delimiter);
				for(int x = 0; x < base.size(); x++)
				{
					if(((String []) base.get(x))[0].equals(row[0]))
					{
						match = true;
						x = base.size();
					}
					
				}
				if(i != 0)
				{
					valid = frame.validinput(row);
				}
				if (!match || valid)
				{
					if(i == 0)
					{
						frame.headerStart(row);
					}
					else
					{
						while(row[0].length() != 5)
						{
							row[0] = "0" + row[0];
						}
						frame.updateTab(row);
					}
					base.put(i, row);
					i++;
				}
			}
			if(!notEmpty)
				frame.emptyFile();
			br.close();
		} catch (IOException e) {
			e.printStackTrace(); //If error, print stack trace
		}
		return base;
	}
	
	public void SaveFile(String FileName, Dictionary<Integer, String[]> dict) {
		try {
			PrintWriter pw = new PrintWriter(FileName);
			for (int i = 0; i < dict.size(); i++) {
				for (int j = 0; j < 6; j++) {
					pw.append(((String[])dict.get(i))[j]);
					pw.append(",");
				}
				pw.append("\n");
			}
			pw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
