package utilities;

import implementations.*;
import exceptions.EmptyQueueException;

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
	 * Checks if closing tag matches the opening tag at top of stack
	 */
	public boolean topStackMatch(String closingTagToCheck)
	{
		String openingTagToCheck = tagStack.peek();
		
		if(closingTagToCheck.length()-1 <= openingTagToCheck.length())
		{
			closingTagToCheck = closingTagToCheck.substring(2, closingTagToCheck.length()-1);
			openingTagToCheck = openingTagToCheck.substring(1,closingTagToCheck.length()+1);
		}
		
		return closingTagToCheck.equals(openingTagToCheck);
	}

	// checks if closing tag matches the front of error queue
	public boolean errorQueueHeadMatch(String tag)
	{
		try {
			String front = errorQueue.peek();
			tag = tag.substring(2, tag.length()-1);
			front = front.substring(1, tag.length()+1);
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
			String cleanOpen = opening.substring(1, cleanClose.length()+1);

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

		int tagStart = -1;
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
				else if(line.charAt(j) == '>' && tagFlag == 1 && line.charAt(j-1) != '/' && line.charAt(tagStart+1) != '?')
				{
					String currentTag = line.substring(tagStart, j+1);

					// handling closing tag
					if(currentTag.substring(0,2).equals("</"))
					{
						// case 1: matches top of stack → valid
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
						System.out.println("Error at line " + errorQueueLine.dequeue() + ": " + errorQueue.dequeue());
					}
				} catch (EmptyQueueException e) {}
			}
		}
	}
