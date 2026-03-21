package implementations;

import java.util.Arrays;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<E> implements ListADT<E> {
	
	private static final int DEFAULT_CAPACITY = 10;
	private Object[] data;
	private int size;
	
	public MyArrayList() {
		data = new Object[DEFAULT_CAPACITY];
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void clear() {
		data = new Object[DEFAULT_CAPACITY];
		size = 0;
		
	}

	@Override
	public boolean add(int index, E toAdd) throws NullPointerException, IndexOutOfBoundsException {
		if(toAdd == null) throw new NullPointerException();
		if(index < 0 || index > size) throw new IndexOutOfBoundsException();
		ensureCapacity();
		for(int i = size; i > index; i--) {
			data[i] = data[i - 1];
		}
		data[index] = toAdd;
		size++;
		return true;
	}

	@Override
	public boolean add(E toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		ensureCapacity();
		data[size] = toAdd;
		size++;
		return true;
	}

	@Override
	public boolean addAll(ListADT<? extends E> toAdd) throws NullPointerException {
		if(toAdd == null) throw new NullPointerException();
		Iterator <? extends E> it = toAdd.iterator();
		while(it.hasNext()) {
			add(it.next());
		}
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		return (E) data[index];
	}

	@Override
	@SuppressWarnings("unchecked")
	public E remove(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		E removed = (E) data[index];
		for(int i = index; i < size - 1; i++) {
			data[i] = data[i + 1];
		}
		data[size - 1] = null;
		size--;
		return removed;
	}

	@Override
	public E remove(E toRemove) throws NullPointerException {
		if(toRemove == null) throw new NullPointerException() ;
		for(int i = 0; i < size; i++) {
			if(toRemove.equals(data[i])) return remove(i);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E set(int index, E toChange) throws NullPointerException, IndexOutOfBoundsException {
		if(toChange == null) throw new NullPointerException();
		if(index < 0 || index >= size) throw new IndexOutOfBoundsException();
		E old = (E) data[index];
		data[index] = toChange;
		return old;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(E toFind) throws NullPointerException {
		if(toFind == null) throw new NullPointerException();
		for(int i = 0; i < size; i++) {
			if(toFind.equals(data[i])) return true;
		}
		return false;
	}

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

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(data, size);
	}

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
	
	public void ensureCapacity() {
		if(size >= data.length) {
			data = Arrays.copyOf(data, data.length * 2);
		}
	}

}
