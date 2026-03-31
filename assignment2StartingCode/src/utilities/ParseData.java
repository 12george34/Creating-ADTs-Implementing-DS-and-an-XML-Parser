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
	
	//trim strings to find the tag
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
	 * Checks if closing tag matches the opening tag at top of stack
	 */
	public boolean topStackMatch(String closingTagToCheck) throws EmptyStackException
	{
		String openingTagToCheck = tagStack.peek();
		openingTagToCheck = getTag(openingTagToCheck);
		closingTagToCheck = getTag(closingTagToCheck);
		return closingTagToCheck.equals(openingTagToCheck);
		
	}

	// checks if closing tag matches the front of error queue
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

	// checks if matching opening tag exists somewhere in stack
	public boolean stackContainsMatch(String tag)
	{
		Object[] arr = tagStack.toArray();

		for(Object obj : arr)
		{
			String opening = (String)obj;
			String cleanClose = tag.substring(2, tag.length()-1);
			String cleanOpen = "";
			//check if lengths of opening and closing tag are similar so the substrings can be compared
			//without this, throws StringIndexOutOfBoundsException
			if(cleanClose.length()<opening.length())
			{
				cleanOpen = opening.substring(1, cleanClose.length()+1);
			}
			
			if(cleanClose.equals(cleanOpen))
			{
				return true;
			}
		}
		return false;
	}

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
		
		for(int i = 0; i < data.size(); i++)
		{
			String line = data.get(i);

			for(int j = 0; j < line.length(); j++)
			{
				// detect start of tag
				if(line.charAt(j) == '<')
				{
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
							//marks start of tag with e* to indicate an error has been found
							currentTag = "e*"+currentTag;
						}
						
					}

					//print test for current tag
					//System.out.println(currentTag);
					
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

							// case 2: matches front of error queue → remove previous error
							else if(errorQueueHeadMatch(currentTag))
							{
								try {
									errorQueueLine.dequeue();
									errorQueue.dequeue();
								} catch (EmptyQueueException e) {}
							}

							// case 3: stack empty → no opening tag exists
							else if(tagStack.isEmpty())
							{
								errorQueueLine.enqueue(i+1);
								errorQueue.enqueue(currentTag);
							}

							// case 4: matching opening tag exists deeper in stack
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

							// case 5: completely unmatched closing tag
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
							errorTag = errorTag.substring(2);
						}
						System.out.println("Error at line " + errorQueueLine.dequeue() + ": " + errorTag);
					}
				} catch (EmptyQueueException e) {}
			}
		}
	}
}
