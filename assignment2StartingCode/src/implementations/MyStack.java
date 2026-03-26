package implementations;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.StackADT;

public class MyStack<E> implements StackADT<E> {

	private static final int DEFAULT_CAPACITY = 10;
	private int capacity;
	private MyArrayList<E> stack;
	private int size;
	
	//constructor for stack of unlimited size
	public MyStack() {
		stack = new MyArrayList<E>();
		size = 0;
	}
	
	//constructor for stack of set capacity
	public MyStack(int capacity) {
		stack = new MyArrayList<E>();
		size = 0;
		this.capacity = capacity;
	}
	@Override
	public void push(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		if(size == 0)
		{
			stack.add(toAdd);
			size++;
		}
		else
		{
			
			//set current top element of stack to placeholder1
			E swapHolder1 = stack.get(0);	
			stack.add(0, toAdd);

			size++;
		}
		
	}

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

	@Override
	public void clear() {
		
		stack.clear();
		size = 0;
		
	}

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
		// TODO Auto-generated method stub
		return null;
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
