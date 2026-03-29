package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import implementations.MyArrayList;


public class LoadData {
	
	/**
	 * method to load data from specified file. Places data into a string version of MyArrayList and returns that list of data to be
	 * parsed later
	 * 
	 * @param fileName file name given to the system in order to load the proper file
	 * @return data returns MyArrayList contain the list of data with each entry being a string of the line from the xml file
	 */
	public static <E> MyArrayList<String> loadData(String fileName){
		
		File myObj = new File(fileName);
		MyArrayList<String> data = new MyArrayList<String>();

	    // try-with-resources: Scanner will be closed automatically
	    try (Scanner myReader = new Scanner(myObj)) 
	    {
	    	while(myReader.hasNextLine()) 
			{
		    	if(myReader.hasNextLine())
		    	{
		    		//set current line of file to line. add line to data list
		    		String line = myReader.nextLine();
		    		data.add(line);
		    	}
			}
	    } catch (FileNotFoundException e) 
	    {
	    	System.out.println("An error occurred.");
	    	e.printStackTrace();
	    }
	    return data;
	}
	
}
