package appDomain;

import implementations.MyArrayList;
import utilities.*;

public class AppDriver
{
	/**
	 *  The main method is the entry point of the application. Loads file name from command line args.
	 *  loads data into an MyArrayList then parses data through ParseData constructor
	 *  
	 *  @param args The input to control the execution of the application.
	 */
	public static void main( String[] args )
	{
		String fileName = "";
		
		//setting file name for test
		//fileName = "res/sample2.xml";
		
		//scans command line args for one ending with .xml which indicates the file name
		for(String arg: args)
		{
			if(arg.toLowerCase().endsWith(".xml"))
			{
				fileName = arg;
			}
		}
		MyArrayList<String> data = LoadData.loadData(fileName);
		new ParseData(data);


	}
}
