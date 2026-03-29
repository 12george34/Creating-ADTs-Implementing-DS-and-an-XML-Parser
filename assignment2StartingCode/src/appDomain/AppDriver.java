package appDomain;

import implementations.MyArrayList;
import implementations.MyStack;
import utilities.*;

public class AppDriver
{
	/**
	 *  The main method is the entry point of the application.
	 *  
	 *  @param args The input to control the execution of the application.
	 */
	public static void main( String[] args )
	{
		String fileName = "";
		
		//setting file name for test
		fileName = "res/sample1.xml";
		MyArrayList<String> data = LoadData.loadData(fileName);
		ParseData parseXML = new ParseData(data);
		
		//test print
		MyStack<String> tagStack = parseXML.getTagStack();
		MyStack<Integer> tagStackLine = parseXML.getTagStackLine();
		int totalSize = tagStack.size();
		System.out.println("Size: "+tagStack.size());
		for(int i = 0; i < totalSize; i++)
		{
			// System.out.println("Size: "+tagStack.size());
			System.out.println("Line #: "+tagStackLine.pop() + " tag: " + tagStack.pop());
			// System.out.println("Index: " + i);
		}

	}
}