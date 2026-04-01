package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

/**
 * This class implements a simple dynamic array list.
 * It stores elements in an array and increases capacity when needed.
 *
 * @param <E> the type of elements stored in the list
 */
public class MyArrayList<E> implements ListADT<E> {
	
	private static final int DEFAULT_CAPACITY = 10;
	private Object[] data;
	private int size;
	
	/**
	 * Creates an empty array list with a default capacity.
	 */
	public MyArrayList() {
		data = new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	/**
	 * Returns the number of elements currently in the list.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Removes all elements from the list and resets it.
	 */
	@Override
	public void clear() {
		data = new Object[DEFAULT_CAPACITY];
		size = 0;
		
	}

	/**
	 * Adds an element at a specific index and shifts elements to the right.
	 */
	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if(toAdd == null) throw new NullPointerException();
		if(index < 0 || index > size) throw new IndexOutOfBoundsException();
		ensureCapacity();
		// Shift elements one position to the right to make room
		for(int i = size; i > index; i--) {
			data[i] = data[i - 1];
		}
		data[index] = toAdd;
		size++;
		return true;
	}

	/**
	 * Adds an element to the end of the list.
	 */
	@Override
	public boolean add(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		ensureCapacity();
		data[size] = toAdd;
		size++;
		return true;
	}

	/**
	 * Adds all elements from another list into this list.
	 */
	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		Iterator <? extends E> it = toAdd.iterator();
		while(it.hasNext()) {
			add(it.next());
		}
		return true;
	}

	/**
	 * Returns the element at the given index.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		return (E) data[index];
	}

	/**
	 * Removes and returns the element at the given index.
	 * Elements after the removed item are shifted left.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E remove(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		E removed = (E) data[index];
		// Shift elements left to fill the removed position
		for(int i = index; i < size - 1; i++) {
			data[i] = data[i + 1];
		}
		data[size - 1] = null;
		size--;
		return removed;
	}

	/**
	 * Removes the first matching element from the list.
	 */
	@Override
	public E remove(E toRemove) throws NullPointerException {
		if(toRemove == null) throw new NullPointerException() ;
		for(int i = 0; i < size; i++) {
			if(toRemove.equals(data[i])) return remove(i);
		}
		return null;
	}

	/**
	 * Replaces the element at the given index and returns the old value.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if(toChange == null) throw new NullPointerException();
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		E old = (E) data[index];
		data[index] = toChange;
		return old;
	}

	/**
	 * Returns true if the list has no elements.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Checks whether the list contains the given element.
	 */
	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if(toFind == null) throw new NullPointerException();
		for(int i = 0; i < size; i++) {
			if(toFind.equals(data[i])) return true;
		}
		return false;
	}

	/**
	 * Copies the list elements into the provided array.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public E[] toArray(E[] toHold) throws NullPointerException {
		if(toHold == null) throw new NullPointerException();
		if(toHold.length < size) toHold = Arrays.copyOf(toHold, size);
		for(int i = 0; i < size; i++) {
			toHold[i] = (E) data[i];
		}
		return toHold;
	}

	/**
	 * Returns the elements in the list as an Object array.
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size);
	}

	/**
	 * Returns an iterator for the list.
	 * A snapshot is used so iteration is based on the current state of the list.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int index = 0;
			private final Object[] snapshot = Arrays.copyOf(data, size);
			
			public boolean hasNext() {
				return index < snapshot.length;
			}
			
			@SuppressWarnings("unchecked")
			public E next() {
				if(!hasNext()) throw new NoSuchElementException();
				return (E) snapshot[index++];
			}

		};
	}
	
	/**
	 * Doubles the array capacity when it becomes full.
	 */
	public void ensureCapacity() {
		if(size >= data.length) {
			data = Arrays.copyOf(data, data.length * 2);
		}
	}

}
