package implementations;


import java.util.EmptyStackException;


import utilities.Iterator;
import utilities.StackADT;
/**
* implements StackADT to creature a first in first out list of data. 
* Utilizes an instance of MyArrayList to generate the list
*/
public class MyStack<E> implements StackADT<E> {

	// set variables. 
	// capacity is maximum capacity of whole stack
	private int capacity;
	//instance of MyArrayList
	private MyArrayList<E> stack;
	//size is current size of active elements within stack. Must be less than or equal to capacity
	private int size;
	

	/**
	*
	*intializes stack as new MyArrayList and sets size to zero. capacity considered unlimited, set to -1 to represent that for stackOverflow check
	*
	*/
	public MyStack() {
		stack = new MyArrayList<E>();
		size = 0;
		//set capacity to -1 for stackOverflow check
		capacity = -1;
	}
	
	/**
	*
	* intializes stack as new MyArrayList and sets size to zero. capacity set to default given
	*
	* @param capacity the maximum capacity to be set for the stack
	*/
	public MyStack(int capacity) {
		stack = new MyArrayList<E>();
		size = 0;
		this.capacity = capacity;
	}
	/**
	*
	* implements push method of StackADT
	* utilizes underlying methods of MyArrayList to add to the stack.
	*
	* @param toAdd element to be added to stack
	*/
	@Override
	public void push(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		//stack currently has no elements, add to stack
		if(size == 0)
		{
			stack.add(toAdd);
			size++;
		}
		//stack currently has no elements, add to stack at index 0
		else
		{
			stack.add(0, toAdd);
			size++;
		}
		
	}
	/**
	*
	* implements pop method of StackADT
	* utilizes underlying methods of MyArrayList to remove the element from the stack
	* element is removed from the stack, size is reduced to reflect updated stack and element removed is returned
	*
	* @return returns the top element of the stack while removing it from the stack
	*/
	@Override
	public E pop() throws EmptyStackException {
		// check if stack is empty. Throw exception if true
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		else 
		{
			size--;
			return stack.remove(0);
		}
	}

	/**
	*
	* implements pop method of StackADT
	* utilizes underlying methods of MyArrayList to get the element from the stack
	* element is left on the top of the stack, size remains the same, element returned
	*
	* @return returns the top element of the stack
	*/
	@Override
	public E peek() throws EmptyStackException {
		// check if stack is empty. Throw exception if true
		if (isEmpty())
		{
			throw new EmptyStackException();
		}
		else
		{
			//return top element of stack.
			return stack.get(0);
		}
	}

	/**
	*
	* implements clear method of StackADT
	* utilizes underlying methods of MyArrayList to get clear the stack
	* stack is cleared, size is reset to 0. capacity remains the same as initialy set (if applicable)
	*
	*/
	@Override
	public void clear() {
		
		stack.clear();
		size = 0;
		
	}
	
	/**
	*
	* implements isEmpty method of StackADT
	* utilizes underlying methods of MyArrayList to get verify if stack is empty
	*
	* @return returns true if stack is confirmed to be empty
	*
	*/
	@Override
	public boolean isEmpty() {
		
		return stack.isEmpty();
	}
	
	@Override
	public Object[] toArray() {

		return stack.toArray();
	}

	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if(holder == null) throw new NullPointerException();
		return stack.toArray(holder);
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException 
	{
		if(toFind == null) throw new NullPointerException();
		for(int i = 0; i < size; i++)
		{
			if(toFind == stack.get(i))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int search(E toFind) {
		for(int i = 0; i < size; i++)
		{
			if(toFind == stack.get(i))
			{
				return i+1;
			}
		}
		return -1;
	}

	
	@Override
	public Iterator<E> iterator() {
		
		return stack.iterator();
	}

	@Override
	public boolean equals(StackADT<E> that) {
		// create a copy of that. this will allow for that elements to be popped without changing that directly
		StackADT<E> thatCopy = that;
		//first check is if size of both stacks is equal. If they are not, return false
		if(that.size() != size)
		{
			return false;
		}
		else
		{
			for(int i = 0; i < size; i++)
			{
				//if stack.get() at index does not equal the popped value of thatCopy, return false
				if(stack.get(i) != thatCopy.pop())
				{
					return false;
				}
			}	
		}
		//all checks past, stacks are equal. Return true
		return true;
	}

	
	@Override
	/**
	 * returns internal size variable
	 */
	public int size() {
		
		return size;
	}

	@Override
	public boolean stackOverflow() {
		if(size == capacity)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
