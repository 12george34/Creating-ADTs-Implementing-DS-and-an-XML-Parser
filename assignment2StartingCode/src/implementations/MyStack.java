package implementations;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.StackADT;

public class MyStack<E> implements StackADT<E> {

	private MyArrayList<E> stack;
	private int size;
	
	public MyStack() {
		stack = new MyArrayList<E>();
		size = 0;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E[] toArray(E[] holder) throws NullPointerException {
		if(holder == null) throw new NullPointerException();
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		return false;
	}
}