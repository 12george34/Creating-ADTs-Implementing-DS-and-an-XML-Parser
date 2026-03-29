package utilities;

import implementations.*;


public class ParseData {

	//two tag stacks meant to work in tandem. One produces the specific xml tag, the other the line the xml tag occurred on
	MyStack<String> tagStack;
	MyStack<Integer> tagStackLine;
	
	//getters for stacks and queues
	public MyStack<String> getTagStack() {
		return tagStack;
	}

	public MyStack<Integer> getTagStackLine() {
		return tagStackLine;
	}

	
	//two tag queues meant to work in tandem. One produces the specific xml tag, the other the line the xml tag occurred on
	//MyQueue<String> errorQueue;
	//MyQueue<Integer> errorQueueLine;
	
	/**
	 * 
	 * @param closingTagToCheck the closing tag being checked against top of stack including </ and > in the tag
	 * @return true if closing tag and opening tag match details. 
	 */
	public boolean topStackMatch(String closingTagToCheck)
	{
		String openingTagToCheck = tagStack.peek();
		//first check is to verify the lengths of both tags to be similar
		//since opening tag can contain additional info, it must be greater or equal to closing tag
		//this is done to avoid scenario where length of closing is out of bound for length of opening
		
		if(closingTagToCheck.length()-1 <= openingTagToCheck.length())
		{
			//removing </ and > from tagToCheck
			closingTagToCheck = closingTagToCheck.substring(2, closingTagToCheck.length()-1);
			openingTagToCheck = openingTagToCheck.substring(1,closingTagToCheck.length()+1);
			// test print
			// System.out.println(closingTagToCheck + " " + openingTagToCheck);
		}
		
		if(closingTagToCheck.equals(openingTagToCheck))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	public ParseData(MyArrayList<String> data) {
		//set tagStack and tagStackLine
		tagStack = new MyStack<String>();
		tagStackLine = new MyStack<Integer>();
		//int used to track when in a string the tag begins. set to -1 as default
		int tagStart = -1;
		//tagFlag marks that the beginning of a tag has been found and thus, the tag needs to be recorded. default is 0
		int tagFlag = 0;
		
		for(int i = 0; i < data.size(); i++)
		{
			String line = data.get(i);
			for(int j = 0; j < line.length(); j++)
			{
				//check if < which indicates beginning of tag
				if(line.charAt(j) == '<')
				{
					//tagStart is set to current j index. tagFlag is raised
					tagStart = j;
					tagFlag = 1;
				}

				//check if > is found, denoting the end of a tag. If > is found and tagFlag is raised, then the tag has been completed
				//also checks if the char before > is /. If tag ends with />, it is self closing and can be ignored
				//also checks if char after < is a ?. Can ignore tags in the format: <?xml somedata=”data”?>
				else if(line.charAt(j) == '>' && tagFlag == 1 && line.charAt(j-1) != '/' && line.charAt(tagStart+1) != '?')
				{
					//check if the start of the tag indicates a closing tag by looking for </ in first two characters
					if(line.substring(tagStart, tagStart+2).equals("</")  && topStackMatch(line.substring(tagStart, j+1)))
					{
						topStackMatch(line.substring(tagStart, j+1));
						tagStackLine.pop();
						tagStack.pop();
					}
					//tag is considered an opening tag, push to stack
					else 
					{
						//first, record the line where tag was found. this is i index + 1
						tagStackLine.push(i+1);
						//second, record the tag in its completion. tag begins at tagStart and ends at index j+1
						tagStack.push(line.substring(tagStart, j+1));
						//reset tagStart and tagFlag
						tagStart = -1;
						tagFlag = 0;
					}
				}
			}
		}
	}
}
