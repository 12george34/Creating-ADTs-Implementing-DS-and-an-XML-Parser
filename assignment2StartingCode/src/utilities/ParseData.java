package utilities;

import implementations.*;
import exceptions.EmptyQueueException;
import exceptions.EmptyStackException;

public class ParseData {

	// two tag stacks meant to work in tandem. One produces the specific xml tag, the other the line the xml tag occurred on
	MyStack<String> tagStack;
	MyStack<Integer> tagStackLine;
	
	// getters for stacks
	public MyStack<String> getTagStack() {
		return tagStack;
	}

	public MyStack<Integer> getTagStackLine() {
		return tagStackLine;
	}

	// queues used to track errors and unmatched tags
	MyQueue<String> errorQueue;
	MyQueue<Integer> errorQueueLine;

	// queue used to store extra closing tags that do not match anything
	MyQueue<String> extrasQueue;
	MyQueue<Integer> extrasQueueLine;
	
	/**
	* removes <,> and / from tags to produce a clean version for comparison to other tags
	*
	* @param tag current tag sent to be trimed, includes <, > or / and potentially other information
	* @return tag returns trimmed tag. example </b> returns as b
	*/
	public String getTag(String tag) 
	{
		tag = tag.replace("<", "");
		tag = tag.replace(">", "");
		tag = tag.replace("/", "");
		
		//looks for first instance of a space. Used to remove explanitory information from tags
		//i.e. PackageCreationLocation FolderName="D:\Document\Product\PL2303\WHQL\Driver\V1.5.0.0" becomes PackageCreationLocation
		if(tag.contains(" "))
		{
			//finds index of first space
			int index = tag.indexOf(" ");
			// truncates tag to a substring prior to first space
			tag = tag.substring(0,index);
		}
		return tag;
	}
	/**
	 * Checks if closing tag matches the opening tag at top of stack after trimming the potential opening and closing tags with getTag()
	 *
	 * @param closingTagToCheck a closing tag denotated by the tag beginning with </. To be compared with top of the stack
	 * @return true if openingTagToCheck and closingTagToCheck equal each other after they have been trimmed via getTag()
	 */
	public boolean topStackMatch(String closingTagToCheck) throws EmptyStackException
	{
		String openingTagToCheck = tagStack.peek();
		openingTagToCheck = getTag(openingTagToCheck);
		closingTagToCheck = getTag(closingTagToCheck);
		return closingTagToCheck.equals(openingTagToCheck);
		
	}

	/**
	 * Checks if tag matches the opening tag at top of errorQueue after trimming the potential tags with getTag()
	 *
	 * @param tag a the tag to be compared to the top of the errorQueue via front
	 * @return true if tag and front equal each other after they have been trimmed via getTag()
	 */
	public boolean errorQueueHeadMatch(String tag)
	{
		try {
			String front = errorQueue.peek();
			tag = getTag(tag);
			front = getTag(front);
			return tag.equals(front);
		} catch (EmptyQueueException e) {
			return false;
		}
	}

	/**
	 * checks if matching opening tag exists somewhere in stack by creating an object array of the tagStack and checking the objects against tag
	 *
	 * @param tag the tag being compared to the existing tags within the stack
	 * @return true if any iteration of cleanClose and cleanOpen are equal after they have trimmed via getTag()
	 */
	public boolean stackContainsMatch(String tag)
	{
		Object[] arr = tagStack.toArray();
	//iterates through the copy of the stack looking for a match
		for(Object obj : arr)
		{
			String opening = (String)obj;
			String cleanClose = getTag(tag);
			String cleanOpen = getTag(opening);
			
			if(cleanClose.equals(cleanOpen))
			{
				return true;
			}
		}
		return false;
	}
	// parse and work with stacks and queues
	public ParseData(MyArrayList<String> data) {

		// initialize stacks
		tagStack = new MyStack<String>();
		tagStackLine = new MyStack<Integer>();

		// initialize queues
		errorQueue = new MyQueue<String>();
		errorQueueLine = new MyQueue<Integer>();
		extrasQueue = new MyQueue<String>();
		extrasQueueLine = new MyQueue<Integer>();
		
		//tagStart is starting index for each line. Set to -1 to indicate no starting index yet
		int tagStart = -1;
		//tagFlag to inform when currently processing a tag. Set to 0 to indicate no tag currently in process
		int tagFlag = 0;

		//loops through data to parse. i represents the current line - 1. For line 1, i is 0
		for(int i = 0; i < data.size(); i++)
		{
			//retrive entire line of data as string
			String line = data.get(i);

			//parses through the current line on a character by character basis. For char 1, j is 0
			for(int j = 0; j < line.length(); j++)
			{
				// detect start of tag by finding first instance of "<"
				if(line.charAt(j) == '<')
				{
					//notes the index the tag begins at. tagFlag raised to denote a tag is currently being processed
					tagStart = j;
					tagFlag = 1;
				}
				// detect end of tag
				// if the character at index j is > and tagFlag is 1, end of a tag has been reached.
				//if character prior to index j is /, means tag ends with /> and is considered self closing and can be ignored
				//if the character immediately after < at the start of the tag is a ?, indicates processing instructions and can be ignored
				else if(line.charAt(j) == '>' && tagFlag == 1 && line.charAt(j-1) != '/' && line.charAt(tagStart+1) != '?')
				{
					String currentTag = line.substring(tagStart, j+1);
					//checks if the are additional '>'at the end of the tag.
					//Used to catch errors such as <Category CategoryName="Unclassified">> where tag ends with '>>'
					//works by checking if there is more line, then checking if the next character is >. If it is, bumps j index up 1 and creates the tag
					if(j+1<line.length())
					{
						if(line.charAt(j+1) == '>')
						{
							//adjusts the tag to include the addtional '>'
							currentTag = line.substring(tagStart, j+2);
							//marks start of tag with e* to indicate an error has been found. e* will not match future tags, so tag will be processed and printed as an error
							currentTag = "e*"+currentTag;
						}
						
					}
					
					// handling closing tag
					if(currentTag.substring(0,2).equals("</"))
					{
						// case 1: matches top of stack → valid
						try {
							if(!tagStack.isEmpty() && topStackMatch(currentTag))
							{
								tagStackLine.pop();
								tagStack.pop();
							}
							// case 2: current tag is on the same line as top tag on stack and the two tags do not equal
							// add both top of stack and current tag to errorQueue
							// catches scenarios like "<b>This is for the Spanish<i> Language that is </b> long ago lost.</i>" where nesting of tags is not correct
							else if(tagStackLine.peek() == i + 1)
							{
								errorQueueLine.enqueue(tagStackLine.pop());
								errorQueue.enqueue(tagStack.pop());
								errorQueueLine.enqueue(i+1);
								errorQueue.enqueue(currentTag);
							}
							// case 3: matches front of error queue → remove previous error
							else if(errorQueueHeadMatch(currentTag))
							{
								try {
									errorQueueLine.dequeue();
									errorQueue.dequeue();
								} catch (EmptyQueueException e) {}
							}

							// case 4: stack empty → no opening tag exists
							else if(tagStack.isEmpty())
							{
								errorQueueLine.enqueue(i+1);
								errorQueue.enqueue(currentTag);
							}

							// case 5: matching opening tag exists deeper in stack
							else if(stackContainsMatch(currentTag))
							{
								// move incorrect tags to error queue until match found
								while(!tagStack.isEmpty() && !topStackMatch(currentTag))
								{
									errorQueueLine.enqueue(tagStackLine.pop());
									errorQueue.enqueue(tagStack.pop());
								}

								// remove matching pair
								tagStackLine.pop();
								tagStack.pop();
							}

							// case 6: completely unmatched closing tag
							else
							{
								extrasQueueLine.enqueue(i+1);
								extrasQueue.enqueue(currentTag);
							}
						} catch (java.util.EmptyStackException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (EmptyStackException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					// handling opening tag
					else
					{
						tagStackLine.push(i+1);
						tagStack.push(currentTag);
					}
					//resets tagStart and tagFlag is lowered
					tagStart = -1;
					tagFlag = 0;
				}
			}
		}

		// process remaining errors and extras
		while(!errorQueue.isEmpty())
		{
			// if no extra tags, print all errors
			if(extrasQueue.isEmpty())
			{
				try {
					System.out.println("Error at line " + errorQueueLine.dequeue() + ": " + errorQueue.dequeue());
				} catch (EmptyQueueException e) {}
			}
			else
			{
				try {
					String errorTag = errorQueue.peek();
					String extraTag = extrasQueue.peek();

					// if extra tag resolves an error, remove both
					if(errorQueueHeadMatch(extraTag))
					{
						errorQueueLine.dequeue();
						errorQueue.dequeue();
						extrasQueueLine.dequeue();
						extrasQueue.dequeue();
					}
					else
					{
						errorTag = errorQueue.dequeue();
						//deals with any tags with the error indicator of 'e*'
						if(errorTag.substring(0,2).equals("e*"))
						{
							//removes 'e*' from tag for print out
							errorTag = errorTag.substring(2);
						}
						System.out.println("Error at line " + errorQueueLine.dequeue() + ": " + errorTag);
					}
				} catch (EmptyQueueException e) {}
			}
		}
	}
}
